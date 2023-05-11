package com.ikikyou.practice.service.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.google.code.kaptcha.Producer;
import com.ikikyou.practice.constant.CacheConstants;
import com.ikikyou.practice.model.entity.system.SysConfig;
import com.ikikyou.practice.service.AuthService;
import com.ikikyou.practice.service.SysConfigService;
import com.ikikyou.practice.utils.Result;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.FastByteArrayOutputStream;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author ikikyou
 * @date 2023/04/13 14:06
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    @Resource
    private RedisTemplate<String, String> redisTemplate;
    @Resource(name = "captchaProducer")
    private Producer captchaProducer;
    @Resource(name = "captchaProducerMath")
    private Producer captchaProducerMath;
    private final SysConfigService configService;

    @Value("${practice.system.captcha.type}")
    private String captchaType;

    @Override
    public Result<Map<String, Object>> getCaptchaImage() {
        Map<String, Object> result = new HashMap<>();
        SysConfig sysConfig = configService.getByKey("sys.account.captchaEnabled");
        boolean captchaEnabled = ObjectUtil.isNotNull(sysConfig);
        result.put("captchaEnabled", captchaEnabled);
        if (!captchaEnabled) {
            return Result.ok();
        }
        // 保存验证码信息
        String uuid = IdUtil.randomUUID();
        String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + uuid;

        String capStr, code = null;
        BufferedImage image = null;
        // 生成验证码
        if ("math".equals(captchaType)) {
            String capText = captchaProducerMath.createText();
            capStr = capText.substring(0, capText.lastIndexOf("@"));
            code = capText.substring(capText.lastIndexOf("@") + 1);
            image = captchaProducerMath.createImage(capStr);
        } else if ("char".equals(captchaType)) {
            capStr = code = captchaProducer.createText();
            image = captchaProducer.createImage(capStr);
        }
        if (code != null) {
            redisTemplate.opsForValue().set(verifyKey, code, 2, TimeUnit.MINUTES);
        }
        // 转换流信息写出
        if (image != null) {
            try (FastByteArrayOutputStream os = new FastByteArrayOutputStream()){
                ImageIO.write(image, "jpg", os);
                result.put("img", Base64.encode(os.toByteArray()));
            } catch (IOException e) {
                return Result.fail(e.getMessage());
            }
        }
        result.put("uuid", uuid);
        return Result.ok(result);
    }
}
