import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
public class TemplateMatching {
	public static void main(String [] args) throws Exception {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		JLabel display = new JLabel();
		JLabel result = new JLabel();
		
		
		JFrame frame = new JFrame("title");
        frame.setLayout(new GridLayout(2, 2));
        

		
		Mat rick = Imgcodecs.imread("./res/target.png");
		Mat target = Imgcodecs.imread("./res/rick_small.png");
		Mat sourceImg = Imgcodecs.imread("./res/picture.jpg");
		Mat resImg = new Mat();
		Mat imgDisplay = new Mat();
		
		sourceImg.copyTo(imgDisplay);
		
		resImg.create(sourceImg.rows(), sourceImg.cols(), CvType.CV_32FC1);
		
		Imgproc.matchTemplate(sourceImg, target, resImg, Imgproc.TM_SQDIFF_NORMED); //useminLoc
		//Imgproc.matchTemplate(sourceImg, target, resImg, Imgproc.TM_SQDIFF); //useminLoc

		//Imgproc.matchTemplate(sourceImg, target, resImg, Imgproc.TM_CCORR); //use maxLoc, bad
		//Imgproc.matchTemplate(sourceImg, target, resImg, Imgproc.TM_CCORR_NORMED); //use maxLoc, bad
		//Imgproc.matchTemplate(sourceImg, target, resImg, Imgproc.TM_CCOEFF); //use maxLoc
		//Imgproc.matchTemplate(sourceImg, target, resImg, Imgproc.TM_CCOEFF_NORMED); //use maxLoc
		
		
		Core.normalize(resImg, resImg, 0, 1, Core.NORM_MINMAX, -1, new Mat());
		Point matchLoc;
		Core.MinMaxLocResult mmr = Core.minMaxLoc(resImg);
		
		
		matchLoc = mmr.minLoc;
		//matchLoc = mmr.maxLoc;
		
		System.out.println("Rick is found at: " + matchLoc.x + ", " + matchLoc.y);
		System.out.println("Rick is found at: " + mmr.maxLoc.x + ", " + mmr.maxLoc.y);
		
		//bgr
		Imgproc.rectangle(sourceImg, matchLoc, new Point(matchLoc.x + rick.cols(), matchLoc.y + rick.rows()),
                new Scalar(0, 0, 255), 2, 8, 0);
        Imgproc.rectangle(resImg, matchLoc, new Point(matchLoc.x + target.cols(), matchLoc.y + target.rows()),
                new Scalar(0, 0, 0), 2, 8, 0);
        display.setIcon(new ImageIcon(HighGui.toBufferedImage(imgDisplay)));
		
		result.setIcon(new ImageIcon(HighGui.toBufferedImage(sourceImg)));
		
		
		
		frame.add(display);
		frame.add(result);
		frame.pack();
		//frame.setMaximumSize(new Dimension(500, 500));
		frame.setVisible(true);
		//IplImage src
	}
}
