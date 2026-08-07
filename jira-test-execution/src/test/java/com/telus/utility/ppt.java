package com.telus.utility;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.Font;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ppt {
	private List<SlideContent> slides;
	private String saveDirectory;

	public ppt() {
		slides = new ArrayList<>();
		// Default to user's documents folder
		saveDirectory = Paths.get(System.getProperty("user.home"), "Documents").toString();
	}

	// Inner class to store slide content
	private class SlideContent {
		String title;
		String subtitle;
		List<String> bulletPoints;
		boolean isTitleSlide;

		SlideContent(String title, String subtitle) {
			this.title = title;
			this.subtitle = subtitle;
			this.isTitleSlide = true;
		}

		SlideContent(String title, String[] points) {
			this.title = title;
			this.bulletPoints = new ArrayList<>();
			for (String point : points) {
				this.bulletPoints.add(point);
			}
			this.isTitleSlide = false;
		}
	}

	// Method to set custom save location
	public void setSaveDirectory(String directory) {
		File dir = new File(directory);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		this.saveDirectory = directory;
	}

	// Method to get the current save directory
	public String getSaveDirectory() {
		return this.saveDirectory;
	}

	public void createTitleSlide(String title, String subtitle) {
		slides.add(new SlideContent(title, subtitle));
	}

	public void createContentSlide(String title, String[] bulletPoints) {
		slides.add(new SlideContent(title, bulletPoints));
	}

	public String savePresentationAsPDF(String fileName) {
		String pdfPath = Paths.get(saveDirectory, fileName + ".pdf").toString();

		try {
			Document document = new Document(PageSize.A4, 50, 50, 50, 50);
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(pdfPath));
			document.open();

			// Define fonts
			Font titleFont = new Font(Font.FontFamily.HELVETICA, 24, Font.BOLD);
			Font subtitleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.NORMAL);
			Font contentFont = new Font(Font.FontFamily.HELVETICA, 14, Font.NORMAL);
			Font bulletFont = new Font(Font.FontFamily.HELVETICA, 14, Font.NORMAL);

			// Create slides
			for (SlideContent slide : slides) {
				document.newPage();

				if (slide.isTitleSlide) {
					// Title slide
					Paragraph title = new Paragraph(slide.title, titleFont);
					title.setAlignment(Element.ALIGN_CENTER);
					title.setSpacingAfter(30);
					document.add(title);

					Paragraph subtitle = new Paragraph(slide.subtitle, subtitleFont);
					subtitle.setAlignment(Element.ALIGN_CENTER);
					document.add(subtitle);
				} else {
					// Content slide
					Paragraph title = new Paragraph(slide.title, titleFont);
					title.setAlignment(Element.ALIGN_LEFT);
					title.setSpacingAfter(30);
					document.add(title);

					for (String point : slide.bulletPoints) {
						Paragraph bullet = new Paragraph();
						bullet.setIndentationLeft(30);
						bullet.add(new Chunk("• ", bulletFont));
						bullet.add(new Chunk(point, contentFont));
						bullet.setSpacingAfter(15);
						document.add(bullet);
					}
				}
			}

			document.close();
			System.out.println("PDF created successfully at: " + pdfPath);
			return pdfPath;

		} catch (Exception e) {
			System.err.println("Error saving presentation as PDF: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	// Example usage
	public static void main(String[] args) {
		ppt creator = new ppt();

		// Set a specific save location (optional)
		String desktopPath = Paths.get(System.getProperty("user.home"), "Desktop").toString();
		creator.setSaveDirectory(desktopPath);

		// Create title slide
		creator.createTitleSlide("My Presentation", "Created by Mohammad Tasneef");

		// Create content slides
		String[] points1 = { "First bullet point", "Second bullet point", "Third bullet point", "Fourth bullet point" };
		creator.createContentSlide("Content Slide 1", points1);

		String[] points2 = { "Another important point", "More information", "Final thoughts" };
		creator.createContentSlide("Content Slide 2", points2);

		// Save the presentation as PDF
		String savedPath = creator.savePresentationAsPDF("MyPresentation");
		if (savedPath != null) {
			System.out.println("PDF saved at: " + savedPath);
		}
	}
}