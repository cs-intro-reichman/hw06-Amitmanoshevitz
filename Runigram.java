// This class uses the Color class, which is part of a package called awt,
// which is part of Java's standard class library.
import java.awt.Color;

/** A library of image processing functions. */
public class Runigram {

	public static void main(String[] args) {
	    
		//// Hide / change / add to the testing code below, as needed.
		
		// Tests the reading and printing of an image:	
		Color[][] tinypic = read("tinypic.ppm");
		print(tinypic);
		

		// Creates an image which will be the result of various 
		// image processing operations:
		Color[][] imageOut;

		// Tests the horizontal flipping of an image:
		imageOut = flippedHorizontally(tinypic);
		System.out.println();
		print(imageOut);
		
		//// Write here whatever code you need in order to test your work.
		//// You can reuse / overide the contents of the imageOut array.
		imageOut = flippedVertically(tinypic);
		System.out.println();
		print(imageOut);

		imageOut = grayScaled(tinypic);
		System.out.println();
		print(imageOut);

	}

	/** Returns a 2D array of Color values, representing the image data
	 * stored in the given PPM file. */
	public static Color[][] read(String fileName) {
		In in = new In(fileName);
		in.readString();
		int numCols = in.readInt();
		int numRows = in.readInt();
		in.readInt();
				Color[][] image = new Color[numRows][numCols];
		int j = 0;
		int i;
		Color tempColor;
		while (j < numRows) { // #feedback - since you run on i and j from 0 to numCols and numRows, consider using a for loop in this case.
			i = 0;
			while (i < numCols) {
				tempColor = new Color(in.readInt(), in.readInt(), in.readInt());
				image[j][i] = tempColor;  
				i++;
			}
			j++;
		}
		return image;
	}

    // Prints the RGB values of a given color.
	private static void print(Color c) {
	    System.out.print("(");
		System.out.printf("%3s,", c.getRed());   // Prints the red component
		System.out.printf("%3s,", c.getGreen()); // Prints the green component
        System.out.printf("%3s",  c.getBlue());  // Prints the blue component
        System.out.print(")  ");
	}

	// Prints the pixels of the given image.
	// Each pixel is printed as a triplet of (r,g,b) values.
	// This function is used for debugging purposes.
	// For example, to check that some image processing function works correctly,
	// we can apply the function and then use this function to print the resulting image.
	private static void print(Color[][] image) {
		int rows = image.length;
		int col = image[0].length;

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < col; j++) {
				// #feedback - you can use the above print(Color c) function instead of implementing it again here.
			System.out.print("(");
			System.out.printf("%3s",image[i][j].getRed()); 
			System.out.print(",");
			System.out.printf("%3s",image[i][j].getGreen()); 
			System.out.print(",");
			System.out.printf("%3s",image[i][j].getBlue());
			System.out.print(")");
			}
			System.out.println();
		}
	}
	
	
	/**
	 * Returns an image which is the horizontally flipped version of the given image. 
	 */
	public static Color[][] flippedHorizontally(Color[][] image) {
		int rows = image.length;
		int col = image[0].length;
		Color[][] image1 = new Color[rows][col];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < col; j++) {
				image1[i][j] = image[i][col - j - 1];
			}
		}
		return image1;
	}
	
	/**
	 * Returns an image which is the vertically flipped version of the given image. 
	 */
	public static Color[][] flippedVertically(Color[][] image) {
		int rows = image.length;
		int col = image[0].length;
		Color[][] image2 = new Color[rows][col];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < col; j++) {
				image2[i][j] = image[rows - i - 1][j];
			}
		}
		return image2;
	}
	
	// Computes the luminance of the RGB values of the given pixel, using the formula 
	// lum = 0.299 * r + 0.587 * g + 0.114 * b, and returns a Color object consisting
	// the three values r = lum, g = lum, b = lum.
		public static Color luminance(Color pixel) {
			double red = 0.299 * pixel.getRed();
			double green = 0.587 * pixel.getGreen();
			double blue = 0.114 * pixel.getBlue();

			int lumVal = (int)(red+green+blue);

			Color lum = new Color(lumVal, lumVal, lumVal);
			return lum;
		}
	
	
	/**
	 * Returns an image which is the grayscaled version of the given image.
	 */
	public static Color[][] grayScaled(Color[][] image) {
		int rows = image.length;
		int col = image[0].length;

		Color[][] image3 = new Color[rows][col];

		for (int i = 0; i <rows; i++) {
			for (int j = 0; j < col; j++) {
				// #feedback - this temp variable is not needed since you don't change image[i][j]. You can do it in one line -
				// image3[i][j] = luminance(image[i][j]);
				Color tempColor = image[i][j];
				tempColor = luminance(tempColor);
				image3 [i][j]= tempColor;
			}
		}
		return image3;
	}
		
	
	/**
	 * Returns an image which is the scaled version of the given image. 
	 * The image is scaled (resized) to have the given width and height.
	 */
	/**
 * Returns an image which is the scaled version of the given image.
 * The image is scaled (resized) to have the given width and height.
 */
public static Color[][] scaled(Color[][] image, int width, int height) {
    double ogwidth = (double)image[0].length/width;
    double ogheight = (double)image.length/height;

    Color[][] image4 = new Color[height][width];

    for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
            int sourceW = (int)(j * ogwidth);
            int sourceH = (int)(i * ogheight);
            image4[i][j] = image[sourceH][sourceW];
        }
    }

    return image4;
}

	/**
	 * Computes and returns a blended color which is a linear combination of the two given
	 * colors. Each r, g, b, value v in the returned color is calculated using the formula 
	 * v = alpha * v1 + (1 - alpha) * v2, where v1 and v2 are the corresponding r, g, b
	 * values in the two input color.
	 */
	public static Color blend(Color c1, Color c2, double alpha) {
		//// Replace the following statement with your code
		double c1red = c1.getRed();
		double c1green = c1.getGreen();
		double c1blue = c1.getBlue();

		double c2red = c2.getRed();
		double c2green = c2.getGreen();
		double c2blue = c2.getBlue();

		int newred = (int)((alpha * c1red) + (1 - alpha)*c2red);
		int newgreen = (int)((alpha * c1green) + (1 - alpha)*c2green);
		int newblue = (int)((alpha * c1blue) + (1 - alpha)*c2blue);
		Color blend = new Color (newred,newgreen, newblue);

		return blend;
	}
	
	/**
	 * Cosntructs and returns an image which is the blending of the two given images.
	 * The blended image is the linear combination of (alpha) part of the first image
	 * and (1 - alpha) part the second image.
	 * The two images must have the same dimensions.
	 */
	public static Color[][] blend(Color[][] image1, Color[][] image2, double alpha) {
		int rows = image1.length;
		int col = image1[0].length;

		Color[][] image5 = new Color[rows][col];

		for (int i = 0; i <rows; i++) {
			for (int j = 0; j < col; j++) {

				// #feedback - also here, this temp is not needed.
				Color tempColor = blend(image1[i][j], image2[i][j], alpha);
				image5 [i][j]= tempColor;
			}
		}
		return image5;
	}

	/**
	 * Morphs the source image into the target image, gradually, in n steps.
	 * Animates the morphing process by displaying the morphed image in each step.
	 * Before starting the process, scales the target image to the dimensions
	 * of the source image.
	 */
	public static void morph(Color[][] source, Color[][] target, int n) {

		Color[][] targetimage = scaled(target, source[0].length, source.length);

		for (int step = 0; step <= n; step++) {

			double weight = (double)(step/n); // #feedback - should be ((n-step)/n)
			Color[][] blendedImage = blend(source, targetimage, weight);
			display(blendedImage);
	
		}
	}
	
	/** Creates a canvas for the given image. */
	public static void setCanvas(Color[][] image) {
		StdDraw.setTitle("Runigram 2023");
		int height = image.length;
		int width = image[0].length;
		StdDraw.setCanvasSize(height, width);
		StdDraw.setXscale(0, width);
		StdDraw.setYscale(0, height);
        // Enables drawing graphics in memory and showing it on the screen only when
		// the StdDraw.show function is called.
		StdDraw.enableDoubleBuffering();
	}

	/** Displays the given image on the current canvas. */
	public static void display(Color[][] image) {
		int height = image.length;
		int width = image[0].length;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				// Sets the pen color to the pixel color
				StdDraw.setPenColor( image[i][j].getRed(),
					                 image[i][j].getGreen(),
					                 image[i][j].getBlue() );
				// Draws the pixel as a filled square of size 1
				StdDraw.filledSquare(j + 0.5, height - i - 0.5, 0.5);
			}
		}
		StdDraw.show();
	}
}

