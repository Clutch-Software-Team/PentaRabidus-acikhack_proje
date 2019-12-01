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

import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;

import com.sun.media.sound.SoftSynthesizer;

import _zem.io.grpc.netty.shaded.io.netty.channel.nio.NioTask;
import zemberek.core.math.FloatArrays;
import zemberek.morphology.TurkishMorphology;
import zemberek.normalization.TurkishSentenceNormalizer;
import zemberek.normalization.TurkishSpellChecker;


public class acik {
	//Kullan�lacak verilerin tan�mlanmas�.
	Vector butunYorumlar=new Vector();
	String[] baglac= {" ama "," fakat "," oysa "," oysaki "," . "," ile "," zira "," e�er "," ise "," k�sacas� "," �ayet "," nitekim "," yine "," gene "," me�er "};
	//Anahtar kelimeler alakal�Ozellikler alt�nda tan�mlanm��t�r.
	String[] alakaliOzellikler= {"klavye","mekanik","tavsiye","tu�","kalite","mouse","rahat","rgb","renk","���k","fiyat","bilek","ses","hissiyat","destek","al�n","performans","makro","sa�lam","sessiz","kullan��l�","farkl�","ba�ar�l�","yeterli","��k","uygun"};
	String[] istenmeyenOzellikler= {"h�zl�","kargo","ge�","geldi","erken","ula�t�","g�n","uzun","sipari�","sat�c�"};
	String[] yorumlar= {""};
	Vector<String> cumleler = new Vector<String>();
    int bir=0,iki=0,dort=0,bes=0;
    float ilkYorumlarinToplami=0;
	float ilkyorumIyiler=0;
	float ilkyorumKotuler=0;
	
	float iyiyorum;
	float kotuyorum;
	
	float totiyi;
	float totkotu;
	
	float totiyiSonuc;
	float totkotuSonuc;
	
	String textArea="";

	public void exxecute() throws IOException {///--------------------------------
		
		
		
		//Dosya Yollar�n�n olu�turulmas�
		String dosyaYolu = "C:\\Users\\Muaz DERVENT\\eclipse-workspace\\acikHack\\src\\acikHack\\AlakaliozelliklerSuzgec.txt";///****************��kt� txt
		String dosyaYolu2 = "C:\\Users\\Muaz DERVENT\\eclipse-workspace\\acikHack\\src\\acikHack\\IstenmeyenozelliklerSuzgec.txt";///****************��kt� txt
		Path lookupRoot = Paths.get("C:\\Users\\Muaz DERVENT\\Desktop\\data\\normalization");
		Path lmFile = Paths.get("C:\\Users\\Muaz DERVENT\\Desktop\\data\\lm\\lm.2gram.slm");
		
		//Normalizer Tan�mlanmas�
		TurkishMorphology morphology = TurkishMorphology.createWithDefaults();
		TurkishSentenceNormalizer normalizer = null;
		try {
		normalizer = new TurkishSentenceNormalizer(morphology, lookupRoot, lmFile);
		} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}

		//Yorumlar�n okunmaya ve i�lenmeye ba�lamas�--------------------------------------------------------
		try {
			String line;
			File dosya = new File("C:\\Users\\Muaz DERVENT\\eclipse-workspace\\acikHack\\src\\acikHack\\dataset.txt");////***********yorumlar.txt
			
			FileOutputStream fos = new FileOutputStream(dosyaYolu);
			OutputStreamWriter osw=new OutputStreamWriter(fos,"UTF-8");
			
			BufferedReader br = new BufferedReader(
					new InputStreamReader(
							new FileInputStream(dosya), "UTF8"));

			while ((line = br.readLine()) != null) {
				//Yorumlar�n Normalize edilmesi.
				line=normalizer.normalize(line);
				//Yorumlar�n ��lenmeden kendi numaraland�rma sistemleri ile puanlar�n�n hesaplanmas�
				//Sonu�lar ile kar��la�t�rmak i�in olu�turulmu�tur.
				switch(line.substring(9, 10)) {
				case "1":{bir++;break;}
				case "2":{iki++;break;}
				case "4":{dort++;break;}
				case "5":{bes++;break;}
				}
				//Yorumlar�n ilk label k�s�mlar�ndan kurtulmas� ve ba�la�lardan c�mleler halinde b�l�n�p depolanmas�
				line=line.substring(9,line.length());
				yorumlar=line.split(" ama | ancak | fakat | oysa | oysaki | k�sacas� ");
				if(yorumlar.length==1) {	
				}else {
				for(int j=0;j<yorumlar.length;j++) {
					String rank=line.substring(0, 2);
					yorumlar[0]=yorumlar[0].substring(2, yorumlar[0].length());
					for(int k=0;k<alakaliOzellikler.length;k++) {
						if(yorumlar[j].contains(alakaliOzellikler[k])) {
							osw.write(rank+" "+yorumlar[j]+"\n");
							break;
						}
					}

				}}	
			}
			osw.close();
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Okuma bittikten sonra kar��la�t�rmak i�in olu�turulan de�erlerin y�zdelikleri hesaplan�yor.
		//5 ve 4 y�ld�zl�lar �r�n� �ven, 1 ve 2 y�ld�zl�lar �r�n� k�t�leyen yorumlar�n y�zdesini hesaplar.
		ilkYorumlarinToplami=bir+iki+dort+bes;
		ilkyorumIyiler=(dort+bes)*100/ilkYorumlarinToplami;
		ilkyorumKotuler=(bir+iki)*100/ilkYorumlarinToplami;
		System.out.println(ilkyorumIyiler);//1
		System.out.println(ilkyorumKotuler);//2
		//Veri tan�mlanmas�
		String[] kontrol= {""};
		int birler=0,ikiler=0,ucler=0,dortler=0,besler=0;	
		//AlakaliozelliklerSuzgec.txt okunmas� ve yorumlar�n i�lenmesi.
		try {
			String line;
			File dosya = new File("C:\\Users\\Muaz DERVENT\\Desktop\\AlakaliozelliklerSuzgec.txt");////***********girdi txt
			
			FileOutputStream fos = new FileOutputStream(dosyaYolu2);
			OutputStreamWriter osw=new OutputStreamWriter(fos,"UTF-8");
			
			BufferedReader br = new BufferedReader(
					new InputStreamReader(
							new FileInputStream(dosya), "UTF8"));
			//�stenmeyen kelimeleri i�eren c�mlelerin temizlenmesi.
			while ((line = br.readLine()) != null) {
				int kotu=0;
				for(int k=0;k<istenmeyenOzellikler.length;k++) {
					if(line.contains(istenmeyenOzellikler[k])) {
						kotu+=1;
						break;
					}
				}
			
				if(kotu==0) {
					cumleler.add(line);
					//Temizlenmi� c�mleler aras�nda kendi puanlama sistemi kullan�larak puanlar�n�n hesaplanmas�.
					switch(line.substring(0, 1)) {
					case "1":{birler++;break;}
					case "2":{ikiler++;break;}
					case "3":{ucler++;break;}
					case "4":{dortler++;break;}
					case "5":{besler++;break;}
					}
					osw.write(line+"\n");
				}
			}
			osw.close();
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Eleme i�lemi bittikten sonra kendi puanlama sistemi kullan�larak hesaplanan de�erleri y�zdeleri hesaplan�yor.
		float toplam=birler+ikiler+dortler+besler;
		 iyiyorum=(besler+dortler)*100/toplam;
		 kotuyorum=(birler+ikiler)*100/toplam;
		System.out.println(iyiyorum);//3
		System.out.println(kotuyorum);//4
		//----------------------------------------------------------------------------------------------------------------
		//Pozitif kelimelerin ve negatif kelimelerin bulundu�u veri setlerinin okunmas�(iyiKelimeler ve kotuKelimeler vektorlerinde depolan�r.)
		Vector<String> iyiKelimeler = new Vector<String>();
		Vector<String> kotuKelimeler = new Vector<String>();
		
		try {
			//Dosya yolu tan�mlanmas�
			String line;
			File dosya = new File("C:\\Users\\Muaz DERVENT\\eclipse-workspace\\acikHack\\src\\acikHack\\iyiKelimeler.txt");////***********girdi txt
			File dosya1 = new File("C:\\Users\\Muaz DERVENT\\eclipse-workspace\\acikHack\\src\\acikHack\\kotuKelimeler.txt");////***********girdi txt

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
		//#-#-#
		//C�mlelerdeki kelimelerin iyiKelime ve kotuKelime veri setleri aras�nda aranarak c�mlenin genel anlamda �r�n� �ven veya k�t�leyen yorum oldu�unu saptamak.
		//�yi kelimeler i�in katsay� 1.333, k�t� kelimeler i�in 0.666 katsay�s� ile �arp�l�yor.(float f de�eri)
		//f de�erinin 1'den b�y�k olmas� �r�n� �ven bir yorum,f de�erinin 1'den k���k olmas� �r�n� k�t�leyen bir yorum anlam�na gelir.
		float f=1;
		 totiyi=0;
		 totkotu=0;
		
		for(int k=0;k<cumleler.size();k++) {
			String[] birC = cumleler.get(k).split(" ");
			for(int i =0;i<birC.length;i++) {
				boolean oldumu=false;
				for(int j=0;j<iyiKelimeler.size();j++) {
					if(birC[i].equals(iyiKelimeler.get(j)) ) {
						f*=1.333;
						oldumu=true;
						break;
					}
				}
				if(!oldumu) {
					for(int j=0;j<kotuKelimeler.size();j++) {
						if(birC[i].equals(kotuKelimeler.get(j)) ) {
							f*=0.666;
							break;
						}	
					}
				}
			}
			if(f>1) {totiyi++;}//�r�n� �ven yorum say�s�
			if(f<1) {totkotu++;}//�r�n� k�t�leyen yorum say�s�
		}
		totiyiSonuc=totiyi*100/(totkotu+totiyi);
		totkotuSonuc=totkotu*100/(totiyi+totkotu);
		System.out.println(totiyi*100/(totkotu+totiyi));//5
		System.out.println(totkotu*100/(totiyi+totkotu));//6
		//--------------------------------------------------------------------------------------
		//Yorumlar�n de�erlendirilmesi ve �r�n �zelliklerine g�re 3 kategori alt�nda toplanmas�=>
		
				//Ar�nd�r�lan yorumlar aras�nda �r�n�n tavsiye edilip-edilmemesinin de�erlendirilmesi...
				Vector<String> tavsiye=new Vector<String>();
				for(int i=0;i<cumleler.size();i++) {
					if(cumleler.get(i).contains("tavsiye ederim")||cumleler.get(i).contains("tavsiye edilir")||cumleler.get(i).contains("tavsiye ediyorum")) {
						tavsiye.add(cumleler.get(i));
					}
				}
				Vector<String> tavsiyeEdilmez=new Vector<String>();
				for(int i=0;i<cumleler.size();i++) {
					if(cumleler.get(i).contains("tavsiye etmem")||cumleler.get(i).contains("tavsiye edilmez")||cumleler.get(i).contains("tavsiye etmiyorum")) {
						tavsiyeEdilmez.add(cumleler.get(i));
					}
				}
						
				float toplamtav=tavsiye.size()+tavsiyeEdilmez.size();
				float tavsiyeYuzde=tavsiye.size()*100/toplamtav;
				float tavsiyeEdilmezYuzde=tavsiyeEdilmez.size()*100/toplamtav;
				System.out.println("Tavsiye edilir:"+tavsiyeYuzde);//7
				System.out.println("Tavsiye Edilmez:"+tavsiyeEdilmezYuzde);//8
				textArea+="Tavsiye edilir:"+tavsiyeYuzde+"\n"+"Tavsiye Edilmez:"+tavsiyeEdilmezYuzde+"\n";
				//Tavsiye blo�unun sonu------------------------------------------------------------------------
				//#-#-#
				//Kalite De�erlendirmesi
				//Yorumlarda �r�n hakk�nda "kaliteli" ve "kalitesiz" olarak kullan�lan kelimelere g�re s�n�fland�r�yoruz.
				Vector<String> kalite=new Vector<String>();
				for(int i=0;i<cumleler.size();i++) {
					if(cumleler.get(i).contains("kaliteli")&&!cumleler.get(i).contains("kaliteli de�il")) {
						kalite.add(cumleler.get(i));
					}
				}
				Vector<String> kalitesiz=new Vector<String>();
				for(int i=0;i<cumleler.size();i++) {
					if(cumleler.get(i).contains("kalitesiz")&&!cumleler.get(i).contains("kalitesiz de�il")) {
						kalitesiz.add(cumleler.get(i));
					}
				}
				float toplamkal=kalite.size()+kalitesiz.size();
				float kalYuzde=kalite.size()*100/toplamkal;
				float kalsizYuzde=kalitesiz.size()*100/toplamkal;
				System.out.println("Kaliteli:"+kalYuzde);//9
				System.out.println("Kalitesiz:"+kalsizYuzde);//10
				textArea+="Kaliteli:"+kalYuzde+"\n"+"Kalitesiz:"+kalsizYuzde+"\n";
				//Kalite de�erlendirmesi sonu------------------------------------------------------------------
				//#-#-#
				//Ka� yorumda �r�n�n fiyat-performans �r�n� oldu�u hesaplan�yor.
				//Genelde kullan�c�lar "fiyat performans" ya da "fiyat-performans" olarak kullan�yorlar.
				//Bizde bu bilgiyi g�z �n�ne alarak yorumlardan "fiyat performans" ge�enleri fp vekt�r�nde depoluyoruz.
				Vector<String> fp=new Vector<String>();
				for(int i=0;i<cumleler.size();i++) {
					if(cumleler.get(i).contains("fiyat-performans")||cumleler.get(i).contains("fiyat performans")) {
						fp.add(cumleler.get(i));
					}
				}
				System.out.println("Fiyat/Performans:"+cumleler.size()+" yorum i�erisinde "+fp.size()+" yorum.");//11
				textArea+="Fiyat/Performans:"+fp.size()+"/"+cumleler.size();
				//Ka� yorumda bahsedilmesi / t�m yorumlar olarak yazd�r�yoruz.
				//Fiyat-Performans de�erlendirmesi sonu------------------------------------------------
	}//------------------------------------------------------------------------------------------------
	
	

}
