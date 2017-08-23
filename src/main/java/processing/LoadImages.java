package processing;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

/**
 * Class Details:-
 * Author: Sarhad
 * User: sarha
 * Date: 22-Aug-17
 * Time : 9:54 PM
 * Project Name: CandyAI
 * Class Name: LoadImages
 */
public class LoadImages
{
	
	/**
	 * This is to set an image resource directory, as different users might use different directories.
	 */
	private String imageResourceDirectory;
	
	/**
	 * This is getter of the image resource directory.
	 */
	public String getImageResourceDirectory()
	{
		return imageResourceDirectory;
	}
	
	/**
	 * This is setter of the image resource directory.
	 */
	public void setImageResourceDirectory(String imageResourceDirectory)
	{
		this.imageResourceDirectory = imageResourceDirectory;
	}
	
	/**
	 * The class that loads the image file.
	 * @throws URISyntaxException   In case of wrong uri
	 * @throws IOException  IOException in case of an error duh
	 */
	private static void imageLoader() throws URISyntaxException, IOException
	{
//		Gives the static resource.
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		URL fileURL = classloader.getResource("image/");
		File file = Paths.get(fileURL.toURI()).toFile();
		
//		Creates a random generator to randomly select a file
		Random rand = new Random();
//		Counts amounts of file in directories (In my case its 1054)
		int amountOfFiles = (int) Files.list(Paths.get(file.getPath())).count();
//		Generates random int
		int randomInt = rand.nextInt(amountOfFiles);
		
		String imageName = String.valueOf(Paths.get(fileURL.toURI()))+"s"+randomInt+".jpg";
		File image = new File(imageName);
	}
	
	
	public static void main(String[] args)
	{
		try
		{
			imageLoader();
		} catch( URISyntaxException | IOException e )
		{
			e.printStackTrace();
		}
	}
	
}
