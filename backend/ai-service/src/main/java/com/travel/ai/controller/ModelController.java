package com.travel.ai.controller;

import com.travel.common.vo.R;
import com.travel.ai.service.ModelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("")
@Tag(name = "模型服务", description = "提供AI模型管理相关接口")
public class ModelController {

    @Resource
    private ModelService modelService;

    @Operation(summary = "获取可用的文本生成模型", description = "获取系统中可用的文本生成模型列表")
    @GetMapping("/model/text/list")
    public R<?> getAvailableTextModels() {
        return R.success(modelService.getAvailableTextModels());
    }

    @Operation(summary = "获取可用的图像识别模型", description = "获取系统中可用的图像识别模型列表")
    @GetMapping("/model/image/list")
    public R<?> getAvailableImageModels() {
        return R.success(modelService.getAvailableImageModels());
    }

    @Operation(summary = "获取默认模型配置", description = "获取系统默认的模型配置")
    @GetMapping("/model/default")
    public R<?> getDefaultModels() {
        return R.success(new DefaultModelsResponse(
                modelService.getDefaultTextModel(),
                modelService.getDefaultImageModel()
        ));
    }

    @Operation(summary = "使用指定模型生成文本", description = "使用指定的模型生成文本")
    @PostMapping("/model/generate")
    public R<?> generateText(@RequestBody TextGenerationRequest request) {
        return R.success(modelService.generateText(request.getModel(), request.getPrompt()));
    }

    @Operation(summary = "使用指定模型识别图像", description = "使用指定的模型识别图像内容")
    @PostMapping("/model/recognize")
    public R<?> recognizeImage(@RequestBody ImageRecognitionRequest request) {
        return R.success(modelService.recognizeImage(request.getModel(), request.getImageUrl()));
    }

    // 请求类
    private static class TextGenerationRequest {
        private String model;
        private String prompt;

        public String getModel() { return model; }
        public void setModel(String model) { this.model = model; }
        public String getPrompt() { return prompt; }
        public void setPrompt(String prompt) { this.prompt = prompt; }
    }

    private static class ImageRecognitionRequest {
        private String model;
        private String imageUrl;

        public String getModel() { return model; }
        public void setModel(String model) { this.model = model; }
        public String getImageUrl() { return imageUrl; }
        public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    }

    // 响应类
    private static class DefaultModelsResponse {
        private String textModel;
        private String imageModel;

        public DefaultModelsResponse(String textModel, String imageModel) {
            this.textModel = textModel;
            this.imageModel = imageModel;
        }

        public String getTextModel() {
            return textModel;
        }

        public void setTextModel(String textModel) {
            this.textModel = textModel;
        }

        public String getImageModel() {
            return imageModel;
        }

        public void setImageModel(String imageModel) {
            this.imageModel = imageModel;
        }
    }
}