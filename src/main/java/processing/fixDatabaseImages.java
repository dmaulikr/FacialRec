package processing;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * Class Details:-
 * Author: Sarhad
 * User: sarha
 * Date: 22-Aug-17
 * Time : 6:29 PM
 * Project Name: CandyAI
 * Class Name: fixDatabaseImages
 *
 * The class just takes care of moving images and naming huge bunch of test images/databases. Please do take care when you are using it. Don't blame me if you delete something without properly manipulating the directories.
 */
public class fixDatabaseImages
{
	
	/**
	 * This is the only time I will use a hardcoded file. Copy your directory containing images here (subdirectories do
	 * not matter) in here.
	 */
	private static File sourceFile = new File("W:\\CandyAI\\src\\main\\resources\\images\\");
	
	/**
	 * Ok, sorry I lied. This is the the only other time I will use a hardcoded directory. Put the directory you want
	 * your images. Remember the directory of storing images. I will be using image under my resource folder
	 */
	private static File destFile = new File("W:\\CandyAI\\src\\main\\resources\\image\\s");
	
	/**
	 * Don't worry about this, just a counter variable.
	 */
	private static int i = 0;
	
	/**
	 * The method loadImage() finds and loads the image and returns the local image. A separate class just for
	 * moduluarity. All images in the development version will be located in resources.
	 *
	 * @param args Default arg
	 */
	public static void main(String[] args)
	{
		try
		{
			renameDatabaseFiles();
		} catch( IOException|URISyntaxException e )
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * The method is used to copy all the image files from whatever way the raw directories are.
	 *
	 * @throws IOException        During file move
	 * @throws URISyntaxException Invalid URI
	 */
	private static void renameDatabaseFiles() throws IOException, URISyntaxException
	{
		moves(sourceFile, destFile);
	}
	
	/**
	 * Recursive function to move the folders and sub-folders to one directory.
	 *
	 * @param sourceFile The source directory/file.
	 * @param destFile   The destination directory/file.
	 */
	private static void moves(File sourceFile, File destFile)
	{
		if( sourceFile.isDirectory() )
		{
			for( File file : sourceFile.listFiles() )
			{
				System.out.println(file.getPath());
				moves(file, destFile);
			}
		} else
		{
			try
			{
				String fileExtension = sourceFile.getPath().substring(sourceFile.getPath().length()-4, sourceFile.getPath().length());
				Files.move(Paths.get(sourceFile.getPath()), Paths.get(destFile.getPath()+i+fileExtension), StandardCopyOption.REPLACE_EXISTING);

//				Increases the count for a unique naming system.
				i = i+1;
				
			} catch( IOException e )
			{
				e.printStackTrace();
			}
		}
	}
}
