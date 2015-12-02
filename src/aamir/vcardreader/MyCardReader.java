package aamir.vcardreader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import ezvcard.VCard;
import ezvcard.VCardVersion;
import ezvcard.io.text.VCardReader;
import ezvcard.io.text.VCardWriter;

public class MyCardReader {

	private static String FILE_PATH;
	
	private String FILE_PATH_WITHOUT_DUPLICATES = " ";//"D:\\.vcf";//FILE_PATH.substring(0, FILE_PATH.length()-3)+"-[NEW-WITHOUT-DUPLICATES].vcf";
	private String FILE_PATH_BACKUP = " ";//"D:\\backup.vcf";//FILE_PATH.substring(0, FILE_PATH.length()-3)+"-[BACKUP].vcf";

	File InputVCardFile;
	
	public List<VCard> vcard_new = new ArrayList<VCard>();
	
	public MyCardReader(String filePath) {
		
		FILE_PATH = filePath;
		System.out.println("FILE_PATH : "+FILE_PATH);
		InputVCardFile = new File(FILE_PATH);
		if( !InputVCardFile.exists()){
			System.out.println("ERROR : NO INPUT FILE FOUND");
			System.exit(-1);
		}
		
		FILE_PATH_WITHOUT_DUPLICATES = FILE_PATH.substring(0, FILE_PATH.length()-4)+"-[NEW-WITHOUT-DUPLICATES].vcf";
		FILE_PATH_BACKUP = FILE_PATH.substring(0, FILE_PATH.length()-3)+"-[BACKUP].vcf";
		
		System.out.println("FILE_PATH_WITHOUT_DUPLICATES : "+FILE_PATH_WITHOUT_DUPLICATES);
		System.out.println("FILE_PATH_BACKUP : "+FILE_PATH_BACKUP);
		
		
	}
	
	public void writeNewVCardDB(){
		List<VCard> withoutDuplicatesVCard = vcard_new;
		
		File file = new File(FILE_PATH_WITHOUT_DUPLICATES);
				
		VCardWriter writer;
		try {
			
			writer = new VCardWriter(file, VCardVersion.V2_1);
			for( int i=0; i<withoutDuplicatesVCard.size(); i++)
				writer.write(withoutDuplicatesVCard.get(i));
			writer.close();
			System.out.println("Write Complete : FILE_PATH_NEW_FILE");
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	public void populateAndRemoveDuplicate() {
		File file = InputVCardFile;
		System.out.println("Reading " + file.getName() + "...");

		try {
			@SuppressWarnings("resource")
			VCardReader reader2 = new VCardReader(file);
			VCard vcard = null;

			int i = 0;
			int dupCount = 0;
			try {
				while ((vcard = reader2.readNext()) != null) {
					dupCount = 0;
					String cellNumber = "no number";
					String name = "no name";

					if (vcard.getTelephoneNumbers().size() != 0)
						cellNumber = vcard.getTelephoneNumbers().get(0)
								.getText();

					if (vcard.getFormattedName() != null)
						name = vcard.getFormattedName().getValue();

					for (int j = 0; j < vcard_new.size(); j++) {
						
						String dupCellNumber = "empty";

						if (vcard_new.get(j).getTelephoneNumbers().size() != 0) {
							dupCellNumber = vcard_new.get(j)
									.getTelephoneNumbers().get(0).getText();
						}// end-if

						if ((cellNumber.contains(dupCellNumber))) {

							/*System.out.println("DUPLICATE : "
									+ cellNumber
									+ " : "
									+ dupCellNumber
									+ " : "
									+ name);*/
							
							dupCount++;
//							System.out.println("**dupCount " + dupCount);
						}
					}// end for

					if (dupCount == 0)
						vcard_new.add(vcard);

//					System.out.println("--------- " + (i + 1) + " -------");
					i++;
				}

				//System.out.println("SIZE OF vcard_new " + vcard_new.size());
				//displayVCard(vcard_new);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public void backupVCard() {
		File inputFile = InputVCardFile;
		InputStream inStream = null;
		OutputStream outStream = null;

		try {
			File backupfile = new File(FILE_PATH_BACKUP);

			inStream = new FileInputStream(inputFile);
			outStream = new FileOutputStream(backupfile);

			byte[] buffer = new byte[1024];

			int length;
			// copy the file content in bytes
			while ((length = inStream.read(buffer)) > 0) {

				outStream.write(buffer, 0, length);

			}

			inStream.close();
			outStream.close();

			System.out.println("File is copied successful!");

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void displayVCard(List<VCard> vcards) {

		for (int i = 0; i < vcards.size(); i++) {

			String cellNumber = "no number";// =
											// vcard_new.get(i).getTelephoneNumbers().get(0).getText();
			String name = "no name";// =
									// vcard_new.get(i).getFormattedName().getValue();

			VCard card = new VCard();

			card = vcards.get(i);

			System.out.println("--------- " + i + " -------");
			if (card.getFormattedName() != null) {
				name = card.getFormattedName().getValue();
				System.out.println("fullName : " + name);
			}
			if (card.getTelephoneNumbers().size() != 0) {

				cellNumber = card.getTelephoneNumbers().get(0).getText();
				System.out.println("Phone number : " + cellNumber);
			}

		}

	}

}
