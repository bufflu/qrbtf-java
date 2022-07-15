## 简介
QRBTF 即 QR code BeauTiFier，一个参数化、程序化的艺术二维码生成器。
本项目参考 [qrbtf.com](https://qrbtf.com/)，通过 Java 语言实现相同效果，方便 Java 开发者使用。
强烈建议体验 [qrbtf.com](https://qrbtf.com/) 中的每一个样式！

## 如何使用
与 [qrbtf.com](https://qrbtf.com/) 中的操作步骤相同：
- 提供文本（如果是网址别忘了 http://）
- 选择样式
- 调整参数
- 获取二维码图片

```java
        // 文本
        String content = "https://qrbtf.com";
        // 选择样式（渲染器）并调整参数
        Renderer renderer = Renderer.rect()
                .adjust()
                .errorCorrectionLevel(ErrorCorrectionLevel.L)
                .anchorPointShape(Shape.RECTANGLE)
                .anchorPointColor(Color.BLUE)
                .anchorPointShape(Shape.ROUNDED_RECTANGLE)
                .dataPointScale(0)
                .end();
        // 生成图片
        BufferedImage image = new QRBtf(renderer).encode(content, null);
        // 输出（这里可以选择不同的方式输出）
        ImageIO.write(image, "png", new File("/Users/bufflu/Desktop/" + System.currentTimeMillis() + ".png"));
```

## 样式与参数
[qrbtf.com](https://qrbtf.com/) 中的样式与 Renderer 的对应关系 [参见](https://www.yuque.com/qrbtf/docs/api)
在 qrbtf-java 中通过 `Renderer.xx()` 选择样式，例如：
```java
        // A1 样式
        RendererRect rendererRect = Renderer.rect();
        
        // A — a1 样式
        RendererLine rendererLine = Renderer.line();
```

每种样式可调整的参数不同，通过 `rendererXxx.adjust()` 来调整，最后以 `end()` 结束，例如:
```java
        // 调整容错率与定位点的颜色
        Renderer renderer = Renderer.rect()
                .adjust()
                .errorCorrectionLevel(ErrorCorrectionLevel.L)
                .anchorPointColor(Color.BLUE)
                .end();
```

## 期待
- 完成所有样式的实现
- 支持 svg 格式
- 使生成的图片更美观
- 性能优化

## 链接
https://github.com/ciaochaos/qrbtf
