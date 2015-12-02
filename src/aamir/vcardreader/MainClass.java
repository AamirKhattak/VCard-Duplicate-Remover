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

import ezvcard.Ezvcard;
import ezvcard.Ezvcard.ParserChainTextReader;
import ezvcard.VCard;
import ezvcard.VCardVersion;
import ezvcard.io.text.VCardReader;
import ezvcard.io.text.VCardWriter;
import ezvcard.property.FormattedName;
import ezvcard.property.Telephone;
import ezvcard.property.VCardProperty;

public class MainClass {

	public static String FILE_PATH;
	public static String BACKUP_FILE_PATH;

	public static void main(String args[]) {
		System.out.println("Use program as follow");
		System.out.println("aamir.vcardreader.MainClass File_Path.vcf");
		
		if( args.length == 1){
			FILE_PATH = args[0];
		}else{
			FILE_PATH = "D:\\PIM00021.vcf";
		}
		
		MyCardReader myCardReader = new MyCardReader(FILE_PATH);

		System.out.println("creating backup\n");
		myCardReader.backupVCard();
		
		System.out.println("Removing Duplicate(s) contacts\n");
		myCardReader.populateAndRemoveDuplicate();
		
		
		System.out.println("New Contacts DB with no Duplicates\n");		
		//mainClass.displayVCard(mainClass.vcard_new);
		myCardReader.writeNewVCardDB();
		System.out.println("sizeof ( vcard_new) : "+ myCardReader.vcard_new.size());
	}// end-main{}
}