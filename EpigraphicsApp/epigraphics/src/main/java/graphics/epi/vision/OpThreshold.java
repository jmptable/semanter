package graphics.epi.vision;

import android.graphics.Bitmap;
import android.util.Log;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.opencv.android.Utils.bitmapToMat;
import static org.opencv.android.Utils.matToBitmap;
import static org.opencv.imgproc.Imgproc.cvtColor;
import static org.opencv.imgproc.Imgproc.threshold;

public class OpThreshold extends VisionOp {
    private Bitmap source, result;
    private boolean finished, cancelled;

    public OpThreshold(VisionListener caller, Bitmap source) {
        super(caller, source);
    }

    public void run() {
        // image into OpenCV
        Mat imageMat = new Mat();
        bitmapToMat(source, imageMat);
        if(cancelled) return;

        // grayscale image
        Mat grayMat = new Mat();
        cvtColor(imageMat, grayMat, Imgproc.COLOR_RGB2GRAY);
        if(cancelled) return;

        // threshold image
        Mat threshMat = new Mat();
        threshold(grayMat, threshMat, 128, 255, Imgproc.THRESH_BINARY);
        if(cancelled) return;

        // image out of OpenCV
        result = Bitmap.createBitmap(source);
        matToBitmap(threshMat, result);

        finish();
    }
}
