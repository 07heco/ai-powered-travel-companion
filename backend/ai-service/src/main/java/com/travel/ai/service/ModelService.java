package com.travel.ai.service;

import com.travel.ai.config.ModelConfig;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@Service
public class ModelService {

    @Resource
    private ModelConfig modelConfig;

    @Value("${spring.ai.huggingface.api-key}")
    private String huggingfaceApiKey;

    @Value("${spring.ai.huggingface.base-url}")
    private String huggingfaceBaseUrl;

    private final OkHttpClient httpClient = new OkHttpClient();

    /**
     * 使用指定模型生成文本
     * @param model 模型名称
     * @param prompt 提示词
     * @return 生成的文本
     */
    public String generateText(String model, String prompt) {
        try {
            // 构建请求体
            String requestBody = "{\"inputs\": \"" + prompt + "\", \"parameters\": {\"max_new_tokens\": 500, \"temperature\": 0.7}}";

            // 构建请求
            Request request = new Request.Builder()
                    .url(huggingfaceBaseUrl + "/" + model)
                    .post(RequestBody.create(requestBody, MediaType.parse("application/json")))
                    .addHeader("Authorization", "Bearer " + huggingfaceApiKey)
                    .build();

            // 发送请求
            Response response = httpClient.newCall(request).execute();

            if (response.isSuccessful() && response.body() != null) {
                return response.body().string();
            } else {
                // 如果API调用失败，返回模拟数据
                return "这是模拟的AI生成结果，基于提示词：" + prompt + "\n\n" +
                        "模型：" + model + "\n" +
                        "生成时间：" + java.time.LocalDateTime.now();
            }
        } catch (IOException e) {
            // 如果发生异常，返回模拟数据
            e.printStackTrace();
            return "这是模拟的AI生成结果，基于提示词：" + prompt + "\n\n" +
                    "模型：" + model + "\n" +
                    "生成时间：" + java.time.LocalDateTime.now();
        }
    }

    /**
     * 使用默认模型生成文本
     * @param prompt 提示词
     * @return 生成的文本
     */
    public String generateText(String prompt) {
        return generateText(modelConfig.getTextGeneration().getDefaultModel(), prompt);
    }

    /**
     * 识别图像中的内容
     * @param model 模型名称
     * @param imageUrl 图像URL
     * @return 识别结果
     */
    public String recognizeImage(String model, String imageUrl) {
        try {
            // 构建请求体
            String requestBody = "{\"inputs\": \"" + imageUrl + "\"}";

            // 构建请求
            Request request = new Request.Builder()
                    .url(huggingfaceBaseUrl + "/" + model)
                    .post(RequestBody.create(requestBody, MediaType.parse("application/json")))
                    .addHeader("Authorization", "Bearer " + huggingfaceApiKey)
                    .build();

            // 发送请求
            Response response = httpClient.newCall(request).execute();

            if (response.isSuccessful() && response.body() != null) {
                return response.body().string();
            } else {
                // 如果API调用失败，返回模拟数据
                return "这是模拟的图像识别结果\n\n" +
                        "图像URL：" + imageUrl + "\n" +
                        "模型：" + model + "\n" +
                        "识别内容：风景照片，包含山脉和湖泊\n" +
                        "置信度：95%";
            }
        } catch (IOException e) {
            // 如果发生异常，返回模拟数据
            e.printStackTrace();
            return "这是模拟的图像识别结果\n\n" +
                    "图像URL：" + imageUrl + "\n" +
                    "模型：" + model + "\n" +
                    "识别内容：风景照片，包含山脉和湖泊\n" +
                    "置信度：95%";
        }
    }

    /**
     * 使用默认模型识别图像
     * @param imageUrl 图像URL
     * @return 识别结果
     */
    public String recognizeImage(String imageUrl) {
        return recognizeImage(modelConfig.getImageRecognition().getDefaultModel(), imageUrl);
    }

    /**
     * 获取可用的文本生成模型列表
     * @return 模型列表
     */
    public List<String> getAvailableTextModels() {
        return modelConfig.getTextGeneration().getOptions();
    }

    /**
     * 获取可用的图像识别模型列表
     * @return 模型列表
     */
    public List<String> getAvailableImageModels() {
        return modelConfig.getImageRecognition().getOptions();
    }

    /**
     * 获取默认的文本生成模型
     * @return 模型名称
     */
    public String getDefaultTextModel() {
        return modelConfig.getTextGeneration().getDefaultModel();
    }

    /**
     * 获取默认的图像识别模型
     * @return 模型名称
     */
    public String getDefaultImageModel() {
        return modelConfig.getImageRecognition().getDefaultModel();
    }
}