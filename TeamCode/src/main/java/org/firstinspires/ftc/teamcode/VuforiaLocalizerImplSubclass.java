package org.firstinspires.ftc.teamcode;

import android.graphics.Bitmap;

import com.vuforia.Frame;
import com.vuforia.Image;
import com.vuforia.PIXEL_FORMAT;
import com.vuforia.State;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.internal.vuforia.VuforiaLocalizerImpl;

public class VuforiaLocalizerImplSubclass extends VuforiaLocalizerImpl {

    public Bitmap bitmap = null;
    public boolean copyNextToBitmap = false;

    class ClosableFrame extends Frame {
        public ClosableFrame(Frame other) {
            super(other);
        }
        public void close() {
            super.delete();
        }
    }

    public class VuforiaCallbackSubclass extends VuforiaCallback {
        @Override
        public synchronized void Vuforia_onUpdate(State state) {
            super.Vuforia_onUpdate(state);
            ClosableFrame frame = new ClosableFrame(state.getFrame());
            long num = frame.getNumImages();
            Image image = null;
            for (int i = 0; i < num; i++) {
                if (frame.getImage(i).getFormat() == PIXEL_FORMAT.RGB565) {
                    image = frame.getImage(i);
                }
            }

            if (copyNextToBitmap) {
                System.out.println("SUBCLASS -> 2");
                if (bitmap == null) {
                    System.out.println("SUBCLASS -> 3");
                    bitmap = Bitmap.createBitmap(image.getWidth(), image.getHeight(), Bitmap.Config.RGB_565);
                }

                System.out.println("SUBCLASS -> 4");
                bitmap.copyPixelsFromBuffer(image.getPixels());
                System.out.println("SUBCLASS -> 5");
                copyNextToBitmap = false;
                System.out.println("SUBCLASS -> value: " + copyNextToBitmap);
            }

            frame.close();
        }
    }

    public VuforiaLocalizerImplSubclass(Parameters parameters) {
        super(parameters);
        stopAR();
        clearGLSurface();
        this.vuforiaCallback = new VuforiaCallbackSubclass();
        startAR();
        Vuforia.setFrameFormat(PIXEL_FORMAT.RGB565, true);
    }

    public void clearGLSurface() {
        if (this.glSurfaceParent != null) {
            appUtil.synchronousRunOnUiThread(new Runnable() {
                @Override
                public void run() {
                    glSurfaceParent.removeAllViews();
                    glSurfaceParent.getOverlay().clear();
                    glSurface = null;
                }
            });
        }
    }

}
