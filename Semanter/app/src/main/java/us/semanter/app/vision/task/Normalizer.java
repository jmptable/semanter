package us.semanter.app.vision.task;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.util.List;

import us.semanter.app.vision.TaskNode;
import us.semanter.app.vision.VisionUtil;

public class Normalizer extends TaskNode {
    public static final String TASK_NAME = "normalize";

    // operation parameters (potentially subject to correction)
    private Mat CLOSE_KERNEL;

    public Normalizer() {
        super();
        init();
    }

    public Normalizer(List<TaskNode> children) {
        super(children);
        init();
    }

    private void init() {
        CLOSE_KERNEL = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(11, 11), new Point(-1, -1));
    }

    public void operateOn(String sourcePath) {
        Mat source = VisionUtil.matFromFile(sourcePath);
        Imgproc.cvtColor(source, source, Imgproc.COLOR_RGBA2RGB);

        /*Mat blurred = new Mat();
        Imgproc.GaussianBlur(source, blurred, new Size(5, 5), 0);*/

        Mat normalized = source.clone();
        Core.normalize(normalized, normalized, 0, 255, Core.NORM_MINMAX);

        /*Mat closed = new Mat(source.width(), source.height(), CvType.CV_8UC3);
        Imgproc.morphologyEx(normalized, closed, Imgproc.MORPH_CLOSE, CLOSE_KERNEL);

        Mat divided = new Mat(source.width(), source.height(), CvType.CV_8UC3);
        Core.divide(normalized, closed, divided);*/

        /*Mat normalized = new Mat(source.width(), source.height(), CvType.CV_8UC1);
        Imgproc.cvtColor(source, normalized, Imgproc.COLOR_RGB2GRAY);

        normalized.convertTo(normalized, -1, 0.1, 1);*/

        /*Imgproc.adaptiveThreshold(normalized, normalized, 255, Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C, Imgproc.THRESH_BINARY, 11, 2);

        Mat mask = new Mat(source.width(), source.height(), CvType.CV_8UC1);
        Imgproc.adaptiveThreshold(normalized, mask, 255, Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C, Imgproc.THRESH_BINARY_INV, 11, 2);
        Imgproc.morphologyEx(mask, mask, Imgproc.MORPH_CLOSE, CLOSE_KERNEL);

        Mat masked = source.clone();//new Mat(source.width(), source.height(), CvType.CV_8UC3, new Scalar(255, 255, 255));
        source.copyTo(masked, mask);
        Imgproc.cvtColor(masked, masked, Imgproc.COLOR_RGBA2RGB);
        Log.d("Normalizer", CvType.CV_8UC3 + " versus " + masked.type());
        Photo.fastNlMeansDenoisingColored(masked, masked);*/

        VisionUtil.saveMat(normalized, bmpConfig, getResultPath(sourcePath).toString());

        dispatch(sourcePath);
    }

    public String getTaskName() {
        return TASK_NAME;
    }
}
