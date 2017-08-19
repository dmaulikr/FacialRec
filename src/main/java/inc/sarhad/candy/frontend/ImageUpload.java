package inc.sarhad.candy.frontend;

import inc.sarhad.candy.exceptions.ImageLimitationError;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

public class ImageUpload extends HttpServlet
{
	
	private final String UPLOAD_DIRECTORY = "C:\\candy\\upload\\user\\images";
	private static String imageName;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		
		//process only if its multipart content
		if( ServletFileUpload.isMultipartContent(request) )
		{
			try
			{
				List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
				
				for( FileItem item : multiparts )
				{
					if( !item.isFormField() )
					{
						File image = new File(item.getName());
//						Actually gets written to the drive. Perform verfication before this
						if( checkImageLimitations(image) )
						{
							String name = renameFile(image.getName());
							imageName = name;
							name = UPLOAD_DIRECTORY+File.separator+name;
							item.write(new File(name));
						}
					}
				}
				
				//File uploaded successfully
				request.setAttribute("message", "File Uploaded Successfully");
				request.setAttribute("image", imageName);
				
			} catch( ImageLimitationError i )
			{
				request.setAttribute("message", "File Upload Failed due to "+i);
			} catch( Exception ex )
			{
				request.setAttribute("message", "File Upload Failed due to "+ex);
			}
			
		} else
		{
			request.setAttribute("message",
					"Sorry this Servlet only handles file upload request");
		}
		
		request.getRequestDispatcher("/result.jsp").forward(request, response);
		
	}
	
	private String renameFile(String name)
	{
		if( name.length()>15 )
		{
			name = name.substring(0, name.length()-6);
		}
		Random rand = new Random(4470);
		name = name+rand.nextDouble();
		return name;
	}
	
	/**
	 * The method checks for image limitation. The limitations include file types, sizes and dimensions.
	 *
	 * @param item The image file itself.
	 *
	 * @return Returns true if the image meets the requirements. Returns false if the image doesn't meet requirements.
	 */
	private Boolean checkImageLimitations(File item) throws IOException, ImageLimitationError
	{
//		This returns size in MB.
		BigDecimal size = new BigDecimal(item.length()).divide(new BigDecimal(1024).multiply(new BigDecimal(1024)), BigDecimal.ROUND_HALF_UP);
		
		if( size.intValueExact()>5 )
		{
			throw new ImageLimitationError("The file size is larger than 5MB. Please upload smaller image.");
		}
		
		if( item.getName().length()<3 )
		{
			throw new ImageLimitationError("The image name is too small. Please change.");
		}
		
		String mimeType = getServletContext().getMimeType(item.getName());
		if( !mimeType.startsWith("image/") )
		{
			throw new ImageLimitationError("The image is of invalid type.");
		}
		
		return true;
	}
	
}