package inc.sarhad.candy.processing;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Class Details:-
 * Author: Sarhad
 * User: sarha
 * Date: 10-Jul-17
 * Time : 3:28 PM
 * Project Name: Candy
 * Class Name: HelloOpenCV
 */
public class HelloOpenCV extends HttpServlet
{
	
	static
	{
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}
	
	private static void doMe(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		Mat m = new Mat(5, 10, CvType.CV_8UC1, new Scalar(0));
		Mat mr1 = m.row(1);
		mr1.setTo(new Scalar(1));
		Mat mc5 = m.col(5);
		mc5.setTo(new Scalar(5));
		
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<body>");
		out.println("<h1>Welcome to OpenCV "+Core.VERSION+"</h1>");
		
		out.println("<p>OpenCV Mat: "+m+"</p>");
		
		out.println("<p>OpenCV Mat data:\n"+m.dump()+"</p>");
		out.println("</body>");
		out.println("</html>");
	}
}
