package annotation.engine;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import java.util.Set;

/**
 * @创建时间 2020/4/21.
 * @开发人员 张戮
 */
@SupportedSourceVersion(SourceVersion.RELEASE_11)
@SupportedAnnotationTypes("annotation.OssClass")
public class OssClassEngine extends AbstractProcessor {

    private Messager messager;

    private Filer filer;

    /**
     * @param annotations 注解类型
     * @param roundEnv    processingEnvironment提供各种工具类  如Elements Filer Types SourceVersion等
     * @return 返回true 表示注解已声明 后续Processor不会再处理  false表示后续Processor会处理他们
     */
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        return false;
    }


}
