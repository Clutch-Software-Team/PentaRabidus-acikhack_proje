package acikHack;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Vector;

import javax.print.DocFlavor.STRING;

import com.sun.media.sound.SoftSynthesizer;

import _zem.io.grpc.netty.shaded.io.netty.channel.nio.NioTask;
import zemberek.morphology.TurkishMorphology;
import zemberek.normalization.TurkishSentenceNormalizer;
import zemberek.normalization.TurkishSpellChecker;


public class den1 {

	public static void main(String[] args) throws IOException {///--------------------------------
		Vector butunYorumlar=new Vector();
		String[] baglac= {" ama "," fakat "," oysa "," oysaki "," . "," ile "," zira "," eðer "," ise "," kýsacasý "," þayet "," nitekim "," yine "," gene "," meðer "};
		String[] alakaliOzellikler= {"klavye","mekanik","tavsiye","tuþ","kalite","mouse","rahat","rgb","renk","ýþýk","fiyat","bilek","ses","hissiyat","destek","alýn","performans","makro","saðlam","sessiz","kullanýþlý","farklý","baþarýlý","yeterli","þýk","uygun"};
		String[] istenmeyenOzellikler= {"hýzlý","kargo","geç","geldi","erken","ulaþtý","gün","uzun","sipariþ","satýcý"};
		String[] yorumlar= {""};
		Vector<String> cumleler = new Vector<String>();
		
		//dosya yolu
		String dosyaYolu = "C:\\Users\\Muaz DERVENT\\Desktop\\AlakaliozelliklerSuzgec.txt";///****************çýktý txt
		String dosyaYolu2 = "C:\\Users\\Muaz DERVENT\\Desktop\\IstenmeyenozelliklerSuzgec.txt";///****************çýktý txt
		Path lookupRoot = Paths.get("C:\\Users\\Muaz DERVENT\\Desktop\\data\\normalization");
		Path lmFile = Paths.get("C:\\Users\\Muaz DERVENT\\Desktop\\data\\lm\\lm.2gram.slm");
		TurkishMorphology morphology = TurkishMorphology.createWithDefaults();
		TurkishSentenceNormalizer normalizer = null;
		try {
		normalizer = new TurkishSentenceNormalizer(morphology, lookupRoot, lmFile);
		} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}

	
		
		
		try {
			
			String line;
			File dosya = new File("C:\\Users\\Muaz DERVENT\\Desktop\\database2.txt");////***********girdi txt
			
			
			FileOutputStream fos = new FileOutputStream(dosyaYolu);
			OutputStreamWriter osw=new OutputStreamWriter(fos,"UTF-8");
			
			
			
			BufferedReader br = new BufferedReader(
					new InputStreamReader(
							new FileInputStream(dosya), "UTF8"));

			while ((line = br.readLine()) != null) {
				line=normalizer.normalize(line);
				
				line=line.substring(11,line.length());
				yorumlar=line.split(" ama | ancak | fakat | oysa | oysaki | kýsacasý ");
				for(int j=0;j<yorumlar.length;j++) {
				//System.out.println(yorumlar[j].trim());
				//butunYorumlar.add(yorumlar[j].trim()+".");
					for(int k=0;k<alakaliOzellikler.length;k++) {
						if(yorumlar[j].contains(alakaliOzellikler[k])) {
							osw.write(yorumlar[j]+"\n");
							break;
						}
					}

				}	
				
			    //System.out.println(line);

			}
			osw.close();
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String[] kontrol= {""};
		
try {
			
			String line;
			File dosya = new File("C:\\Users\\Muaz DERVENT\\Desktop\\AlakaliozelliklerSuzgec.txt");////***********girdi txt
			
			
			FileOutputStream fos = new FileOutputStream(dosyaYolu2);
			OutputStreamWriter osw=new OutputStreamWriter(fos,"UTF-8");
			
			
			
			BufferedReader br = new BufferedReader(
					new InputStreamReader(
							new FileInputStream(dosya), "UTF8"));

			while ((line = br.readLine()) != null) {
			int kotu=0;
							//osw.write(yorumlar[j]+"\n");
				for(int k=0;k<istenmeyenOzellikler.length;k++) {
					if(line.contains(istenmeyenOzellikler[k])) {
						kotu+=1;
						break;
					}
				}
				
				if(kotu==0) {
					cumleler.add(line);
					osw.write(line+"\n");
				}
				
				
				
			    //System.out.println(line);

			}
			osw.close();
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Vector<String> iyiKelimeler = new Vector<String>();
		Vector<String> kotuKelimeler = new Vector<String>();
		try {
			
			String line;
			File dosya = new File("C:\\Users\\Muaz DERVENT\\Desktop\\positive_words_tr.txt");////***********girdi txt
			File dosya1 = new File("C:\\Users\\Muaz DERVENT\\Desktop\\negative_words_tr.txt");////***********girdi txt

			BufferedReader br = new BufferedReader(
					new InputStreamReader(
							new FileInputStream(dosya), "UTF8"));

			while ((line = br.readLine()) != null) {
				iyiKelimeler.add(line);

			}
			BufferedReader br1 = new BufferedReader(
					new InputStreamReader(
							new FileInputStream(dosya1), "UTF8"));

			while ((line = br1.readLine()) != null) {
				kotuKelimeler.add(line);

			}
			
			br.close();
			br1.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		float f=1;
		int totiyi=0;
		int totkotu=0;
		String[] birC = cumleler.get(450).split(" ");
		
		for(int i =0;i<birC.length;i++) {
			birC[i].trim();
			
			boolean oldumu=false;
			for(int j=0;j<iyiKelimeler.size();j++) {
				if(birC[i].equals(iyiKelimeler.get(j)) ) {
					f*=1.333;
					oldumu=true;
					System.out.println("iyi "+iyiKelimeler.get(j));
					break;
				}
			}
			if(!oldumu) {
				for(int j=0;j<kotuKelimeler.size();j++) {
					if(birC[i].equals(kotuKelimeler.get(j)) ) {
						f*=0.666;
						System.out.println("kotu "+kotuKelimeler.get(j));
						break;
					}	
				}
			}
			
			
			
			
		}
		if(f>1) {totiyi++;}
		if(f<1) {totkotu++;}
		System.out.println("f="+f);
		System.out.println("totiyi= "+totiyi+"\n tot kötü="+totkotu+"\n cümle ="+cumleler.get(450));
		
		
		
		
		

				
				
				
				
				
	}//------------------------------------------------------------------------------------------------

}
