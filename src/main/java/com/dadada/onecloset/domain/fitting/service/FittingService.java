package com.dadada.onecloset.domain.fitting.service;

import com.dadada.onecloset.domain.clothes.entity.Clothes;
import com.dadada.onecloset.domain.clothes.repository.ClothesRepository;
import com.dadada.onecloset.domain.codi.dto.request.CodiRegistRequestDto;
import com.dadada.onecloset.domain.codi.dto.response.CodiListResponseDto;
import com.dadada.onecloset.domain.codi.entity.Codi;
import com.dadada.onecloset.domain.codi.repository.CodiRepository;
import com.dadada.onecloset.domain.fitting.dto.FittingCheckDataDto;
import com.dadada.onecloset.domain.fitting.dto.FittingModelRegistDataDto;
import com.dadada.onecloset.domain.fitting.dto.request.FittingDateUpdateRequestDto;
import com.dadada.onecloset.domain.fitting.dto.request.FittingRequestDto;
import com.dadada.onecloset.domain.fitting.dto.request.FittingSaveRequestDto;
import com.dadada.onecloset.domain.fitting.dto.response.FittingAndCodiResponseDto;
import com.dadada.onecloset.domain.fitting.dto.response.FittingListResponseDto;
import com.dadada.onecloset.domain.fitting.dto.response.FittingResultResponseDto;
import com.dadada.onecloset.domain.fitting.dto.response.ModelListResponseDto;
import com.dadada.onecloset.domain.fitting.entity.Fitting;
import com.dadada.onecloset.domain.fitting.entity.FittingClothes;
import com.dadada.onecloset.domain.fitting.entity.FittingModel;
import com.dadada.onecloset.domain.fitting.repository.FittingClothesRepository;
import com.dadada.onecloset.domain.fitting.repository.FittingModelRepository;
import com.dadada.onecloset.domain.fitting.repository.FittingRepository;
import com.dadada.onecloset.domain.user.entity.User;
import com.dadada.onecloset.domain.user.repository.UserRepository;
import com.dadada.onecloset.exception.CustomException;
import com.dadada.onecloset.exception.ExceptionType;
import com.dadada.onecloset.fastapi.FastApiFittingRequestDto;
import com.dadada.onecloset.fastapi.FastApiModelRegistResponseDto;
import com.dadada.onecloset.fastapi.FastApiService;
import com.dadada.onecloset.global.CommonResponse;
import com.dadada.onecloset.global.DataResponse;
import com.dadada.onecloset.global.S3Service;
import com.dadada.onecloset.util.WebClientUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FittingService {

    @Value("${AI_SERVER}")
    private String AI_SERVER;

    private final WebClientUtil webClientUtil;

    private final FastApiService fastApiService;
    private final UserRepository userRepository;
    private final ClothesRepository clothesRepository;

    private final FittingRepository fittingRepository;
    private final FittingModelRepository fittingModelRepository;
    private final FittingClothesRepository fittingClothesRepository;

    private final CodiRepository codiRepository;

    private final S3Service s3Service;

    @Transactional
    public FittingModelRegistDataDto registFittingModel(MultipartFile multipartFile, Long userId) throws IOException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ExceptionType.USER_NOT_FOUND));
        String url = s3Service.upload(multipartFile);

        FittingModel fittingModel = FittingModel.builder()
                .user(user)
                .originImg(url)
                .build();

        FittingModel saveFittingModel = fittingModelRepository.save(fittingModel);

        return FittingModelRegistDataDto
                .builder()
                .fittingModel(saveFittingModel)
                .url(url)
                .build();
    }

    @Transactional
    public CommonResponse getModelInfoAndUpdateFittingModel(FittingModelRegistDataDto registDataDto){

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("image", registDataDto.getUrl());
        log.info("모델등록 실행전");
        webClientUtil.post(AI_SERVER + "/fitting/preprocess", jsonObject, String.class)
        .subscribe(
                response -> {
                    System.out.println(response);
                    ObjectMapper objectMapper = new ObjectMapper();
                    try {
                        FastApiModelRegistResponseDto responseDto = objectMapper.readValue(response, FastApiModelRegistResponseDto.class);
                        FittingModel fittingModel = registDataDto.getFittingModel();
                        fittingModel.updateInfo(responseDto);
                        fittingModelRepository.save(fittingModel);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                },
                error -> {
                    log.error(error.getMessage());
                    log.error("registFittingModel ERR");
                }
        );
        log.info("모델 등록 실행 후");
//        FastApiModelRegistResponseDto responseDto = fastApiService.registFittingModel(registDataDto.getUrl());
//        FittingModel fittingModel = registDataDto.getFittingModel();
//        fittingModel.updateInfo(responseDto);
        return new CommonResponse(200, "모델이 등록되었습니다.");
    }

    public DataResponse<List<ModelListResponseDto>> getFittingModelList(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ExceptionType.USER_NOT_FOUND));
        List<FittingModel> fittingModelList = fittingModelRepository.findByUserWhereStatusIsTrue(user);
        List<ModelListResponseDto> responseDtoList = new ArrayList<>();
        for (FittingModel fittingModel : fittingModelList) {
            responseDtoList.add(ModelListResponseDto.of(fittingModel));
        }
        return new DataResponse<>(200, "가상 피팅 모델 리스트 조회", responseDtoList);
    }

    @Transactional
    public CommonResponse deleteModel(Long modelId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ExceptionType.USER_NOT_FOUND));
        FittingModel fittingModel = fittingModelRepository.findByIdAndUserWhereStatusIsTrue(modelId, user)
                .orElseThrow(() -> new CustomException(ExceptionType.MODEL_NOT_FOUND));
        fittingModel.deleteModel();
        return new CommonResponse(200, "모델 삭제 성공");
    }

    public DataResponse<FittingResultResponseDto> fitting(FittingRequestDto requestDto, Long userId) throws JsonProcessingException {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ExceptionType.USER_NOT_FOUND));
        FittingModel fittingModel = fittingModelRepository.findByIdAndUserWhereStatusIsTrue(requestDto.getModelId(), user)
                .orElseThrow(() -> new CustomException(ExceptionType.MODEL_NOT_FOUND));

        FittingCheckDataDto checkDataDto = checkFitting(requestDto, user);
        if (!checkDataDto.getCheck()) {
            return new DataResponse<>(400, "가상피팅 가능한 조합이 아닙니다.");
        }

        String fittingImg = fastApiService.fitting(checkDataDto.getFittingRequestDtoList(), fittingModel);

        FittingResultResponseDto responseDto = FittingResultResponseDto
                .builder()
                .originImg(fittingModel.getOriginImg())
                .fittingImg(fittingImg)
                .modelId(requestDto.getModelId())
                .clothesInfoList(checkDataDto.getFittingRequestDtoList())
                .build();

        return new DataResponse<>(200, "가상피팅 완료", responseDto);

    }

    @Transactional
    public DataResponse<Long> saveFitting(FittingSaveRequestDto requestDto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ExceptionType.USER_NOT_FOUND));
        FittingModel fittingModel = fittingModelRepository.findByIdAndUserWhereStatusIsTrue(requestDto.getModelId(), user)
                .orElseThrow(() -> new CustomException(ExceptionType.MODEL_NOT_FOUND));

        Fitting fitting = Fitting
                .builder()
                .fittingModel(fittingModel)
                .fittingImg(requestDto.getFittingImg())
                .fittingThumnailImg(requestDto.getFittingImg())
                .user(user)
                .build();

        Fitting fittingSave = fittingRepository.save(fitting);

        for (Long clothesId : requestDto.getClothesIdList()) {
            Clothes clothes = clothesRepository.findByIdAndUser(clothesId, user)
                    .orElseThrow(() -> new CustomException(ExceptionType.CLOTHES_NOT_FOUND));
            FittingClothes fittingClothes = FittingClothes.builder()
                    .clothes(clothes)
                    .fitting(fittingSave)
                    .build();
            fittingClothesRepository.save(fittingClothes);
        }

        return new DataResponse<>(200, "가상피팅 저장완료", fittingSave.getId());
    }

    @Transactional
    public DataResponse<Long> saveFittingAndTime(FittingSaveRequestDto requestDto, String wearingAtDay, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ExceptionType.USER_NOT_FOUND));
        FittingModel fittingModel = fittingModelRepository.findByIdAndUserWhereStatusIsTrue(requestDto.getModelId(), user)
                .orElseThrow(() -> new CustomException(ExceptionType.MODEL_NOT_FOUND));

        Fitting fitting = Fitting
                .builder()
                .fittingModel(fittingModel)
                .fittingImg(requestDto.getFittingImg())
                .fittingThumnailImg(requestDto.getFittingImg())
                .wearingAtDay(wearingAtDay)
                .wearingAtMonth(wearingAtDay.substring(0, 7))
                .user(user)
                .build();

        Fitting fittingSave = fittingRepository.save(fitting);

        for (Long clothesId : requestDto.getClothesIdList()) {
            Clothes clothes = clothesRepository.findByIdAndUser(clothesId, user)
                    .orElseThrow(() -> new CustomException(ExceptionType.CLOTHES_NOT_FOUND));
            FittingClothes fittingClothes = FittingClothes.builder()
                    .clothes(clothes)
                    .fitting(fittingSave)
                    .build();
            fittingClothesRepository.save(fittingClothes);
        }

        return new DataResponse<>(200, "가상피팅 저장완료", fittingSave.getId());
    }

    @Transactional
    public CommonResponse changeWearingAt(FittingDateUpdateRequestDto requestDto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ExceptionType.USER_NOT_FOUND));
        Fitting fitting = fittingRepository.findByIdAndUser(requestDto.getFittingId(), user)
                .orElseThrow(() -> new CustomException(ExceptionType.FITTING_NOT_FOUND));
        String wearingAtDay = requestDto.getWearingAt();
        fitting.editWearingAt(wearingAtDay.substring(0, 7), wearingAtDay);
        return new CommonResponse(200, "날짜 등록/수정 성공");
    }

    @Transactional
    public CommonResponse deleteFitting(Long fittingId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ExceptionType.USER_NOT_FOUND));
        Fitting fitting = fittingRepository.findByIdAndUser(fittingId, user)
                .orElseThrow(() -> new CustomException(ExceptionType.FITTING_NOT_FOUND));
        fittingRepository.delete(fitting);
        return new CommonResponse(200, "의류 삭제 완료");
    }

    public DataResponse<FittingAndCodiResponseDto> getFittingAndCodiListByMonth(String date, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ExceptionType.USER_NOT_FOUND));
        List<Fitting> fittingList = fittingRepository.findByWearingAtMonthAndUser(date, user);
        List<Codi> codiList = codiRepository.findByWearingAtMonthAndUser(date, user);

        FittingAndCodiResponseDto responseDto = getFittingAndCodiResponseDto(fittingList, codiList);

        return new DataResponse<>(200, "가상피팅/코디 전체 조회", responseDto);
    }

    public DataResponse<FittingAndCodiResponseDto> getFittingAndCodiList(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ExceptionType.USER_NOT_FOUND));

        List<Fitting> fittingList = fittingRepository.findByUser(user);
        List<Codi> codiList = codiRepository.findByUser(user);

        FittingAndCodiResponseDto responseDto = getFittingAndCodiResponseDto(fittingList, codiList);

        return new DataResponse<>(200, "가상피팅/코디 월별 조회", responseDto);
    }

    public FittingCheckDataDto checkFitting(FittingRequestDto requestDto, User user) {
        Clothes upper = clothesRepository.findByIdAndUser(requestDto.getUpperId(), user).orElse(null);
        Clothes lower = clothesRepository.findByIdAndUser(requestDto.getBottomId(), user).orElse(null);
        Clothes dresses = clothesRepository.findByIdAndUser(requestDto.getOnepieceId(), user).orElse(null);

        int checkNun = 0;
        boolean check = true;
        List<FastApiFittingRequestDto> requestDtoList = new ArrayList<>();

        if (upper != null) {
            if (upper.getType().getUpperTypeCode().getUpperTypeName().equals("상의")) {
                checkNun++;
                requestDtoList.add(FastApiFittingRequestDto.of(upper.getId(), "upper", upper.getOriginImg()));
            }
        }

        if (lower != null) {
            if (lower.getType().getUpperTypeCode().getUpperTypeName().equals("하의")) {
                checkNun++;
                requestDtoList.add(FastApiFittingRequestDto.of(lower.getId(), "lower", lower.getOriginImg()));
            }
        }
        if (dresses != null) {
            if (dresses.getType().getUpperTypeCode().getUpperTypeName().equals("한벌옷")) {
                checkNun = checkNun + 2;
                requestDtoList.add(FastApiFittingRequestDto.of(dresses.getId(), "dress", dresses.getOriginImg()));
            }
        }

        if (checkNun == 0 || checkNun > 2)
            check = false;

        return FittingCheckDataDto
                .builder()
                .check(check)
                .fittingRequestDtoList(requestDtoList)
                .build();

    }

    public FittingAndCodiResponseDto getFittingAndCodiResponseDto(List<Fitting> fittingList, List<Codi> codiList) {

        List<FittingListResponseDto> fittingListResponseDtoList = new ArrayList<>();
        List<CodiListResponseDto> codiListResponseDtoList = new ArrayList<>();

        for (Fitting fitting: fittingList) {
            fittingListResponseDtoList.add(FittingListResponseDto.of(fitting));
        }
        for (Codi codi: codiList) {
            codiListResponseDtoList.add(CodiListResponseDto.of(codi));
        }

        return FittingAndCodiResponseDto
                .builder()
                .fittingList(fittingListResponseDtoList)
                .codiList(codiListResponseDtoList)
                .build();
    }

}
