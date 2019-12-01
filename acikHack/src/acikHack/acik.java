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
	//Kullanýlacak verilerin tanýmlanmasý.
	Vector butunYorumlar=new Vector();
	String[] baglac= {" ama "," fakat "," oysa "," oysaki "," . "," ile "," zira "," eðer "," ise "," kýsacasý "," þayet "," nitekim "," yine "," gene "," meðer "};
	//Anahtar kelimeler alakalýOzellikler altýnda tanýmlanmýþtýr.
	String[] alakaliOzellikler= {"klavye","mekanik","tavsiye","tuþ","kalite","mouse","rahat","rgb","renk","ýþýk","fiyat","bilek","ses","hissiyat","destek","alýn","performans","makro","saðlam","sessiz","kullanýþlý","farklý","baþarýlý","yeterli","þýk","uygun"};
	String[] istenmeyenOzellikler= {"hýzlý","kargo","geç","geldi","erken","ulaþtý","gün","uzun","sipariþ","satýcý"};
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
		
		
		
		//Dosya Yollarýnýn oluþturulmasý
		String dosyaYolu = "C:\\Users\\Muaz DERVENT\\eclipse-workspace\\acikHack\\src\\acikHack\\AlakaliozelliklerSuzgec.txt";///****************çýktý txt
		String dosyaYolu2 = "C:\\Users\\Muaz DERVENT\\eclipse-workspace\\acikHack\\src\\acikHack\\IstenmeyenozelliklerSuzgec.txt";///****************çýktý txt
		Path lookupRoot = Paths.get("C:\\Users\\Muaz DERVENT\\Desktop\\data\\normalization");
		Path lmFile = Paths.get("C:\\Users\\Muaz DERVENT\\Desktop\\data\\lm\\lm.2gram.slm");
		
		//Normalizer Tanýmlanmasý
		TurkishMorphology morphology = TurkishMorphology.createWithDefaults();
		TurkishSentenceNormalizer normalizer = null;
		try {
		normalizer = new TurkishSentenceNormalizer(morphology, lookupRoot, lmFile);
		} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}

		//Yorumlarýn okunmaya ve iþlenmeye baþlamasý--------------------------------------------------------
		try {
			String line;
			File dosya = new File("C:\\Users\\Muaz DERVENT\\eclipse-workspace\\acikHack\\src\\acikHack\\dataset.txt");////***********yorumlar.txt
			
			FileOutputStream fos = new FileOutputStream(dosyaYolu);
			OutputStreamWriter osw=new OutputStreamWriter(fos,"UTF-8");
			
			BufferedReader br = new BufferedReader(
					new InputStreamReader(
							new FileInputStream(dosya), "UTF8"));

			while ((line = br.readLine()) != null) {
				//Yorumlarýn Normalize edilmesi.
				line=normalizer.normalize(line);
				//Yorumlarýn Ýþlenmeden kendi numaralandýrma sistemleri ile puanlarýnýn hesaplanmasý
				//Sonuçlar ile karþýlaþtýrmak için oluþturulmuþtur.
				switch(line.substring(9, 10)) {
				case "1":{bir++;break;}
				case "2":{iki++;break;}
				case "4":{dort++;break;}
				case "5":{bes++;break;}
				}
				//Yorumlarýn ilk label kýsýmlarýndan kurtulmasý ve baðlaçlardan cümleler halinde bölünüp depolanmasý
				line=line.substring(9,line.length());
				yorumlar=line.split(" ama | ancak | fakat | oysa | oysaki | kýsacasý ");
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
		//Okuma bittikten sonra karþýlaþtýrmak için oluþturulan deðerlerin yüzdelikleri hesaplanýyor.
		//5 ve 4 yýldýzlýlar ürünü öven, 1 ve 2 yýldýzlýlar ürünü kötüleyen yorumlarýn yüzdesini hesaplar.
		ilkYorumlarinToplami=bir+iki+dort+bes;
		ilkyorumIyiler=(dort+bes)*100/ilkYorumlarinToplami;
		ilkyorumKotuler=(bir+iki)*100/ilkYorumlarinToplami;
		System.out.println(ilkyorumIyiler);//1
		System.out.println(ilkyorumKotuler);//2
		//Veri tanýmlanmasý
		String[] kontrol= {""};
		int birler=0,ikiler=0,ucler=0,dortler=0,besler=0;	
		//AlakaliozelliklerSuzgec.txt okunmasý ve yorumlarýn iþlenmesi.
		try {
			String line;
			File dosya = new File("C:\\Users\\Muaz DERVENT\\Desktop\\AlakaliozelliklerSuzgec.txt");////***********girdi txt
			
			FileOutputStream fos = new FileOutputStream(dosyaYolu2);
			OutputStreamWriter osw=new OutputStreamWriter(fos,"UTF-8");
			
			BufferedReader br = new BufferedReader(
					new InputStreamReader(
							new FileInputStream(dosya), "UTF8"));
			//Ýstenmeyen kelimeleri içeren cümlelerin temizlenmesi.
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
					//Temizlenmiþ cümleler arasýnda kendi puanlama sistemi kullanýlarak puanlarýnýn hesaplanmasý.
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
		//Eleme iþlemi bittikten sonra kendi puanlama sistemi kullanýlarak hesaplanan deðerleri yüzdeleri hesaplanýyor.
		float toplam=birler+ikiler+dortler+besler;
		 iyiyorum=(besler+dortler)*100/toplam;
		 kotuyorum=(birler+ikiler)*100/toplam;
		System.out.println(iyiyorum);//3
		System.out.println(kotuyorum);//4
		//----------------------------------------------------------------------------------------------------------------
		//Pozitif kelimelerin ve negatif kelimelerin bulunduðu veri setlerinin okunmasý(iyiKelimeler ve kotuKelimeler vektorlerinde depolanýr.)
		Vector<String> iyiKelimeler = new Vector<String>();
		Vector<String> kotuKelimeler = new Vector<String>();
		
		try {
			//Dosya yolu tanýmlanmasý
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
		//Cümlelerdeki kelimelerin iyiKelime ve kotuKelime veri setleri arasýnda aranarak cümlenin genel anlamda ürünü öven veya kötüleyen yorum olduðunu saptamak.
		//Ýyi kelimeler için katsayý 1.333, kötü kelimeler için 0.666 katsayýsý ile çarpýlýyor.(float f deðeri)
		//f deðerinin 1'den büyük olmasý ürünü öven bir yorum,f deðerinin 1'den küçük olmasý ürünü kötüleyen bir yorum anlamýna gelir.
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
			if(f>1) {totiyi++;}//Ürünü öven yorum sayýsý
			if(f<1) {totkotu++;}//Ürünü kötüleyen yorum sayýsý
		}
		totiyiSonuc=totiyi*100/(totkotu+totiyi);
		totkotuSonuc=totkotu*100/(totiyi+totkotu);
		System.out.println(totiyi*100/(totkotu+totiyi));//5
		System.out.println(totkotu*100/(totiyi+totkotu));//6
		//--------------------------------------------------------------------------------------
		//Yorumlarýn deðerlendirilmesi ve ürün özelliklerine göre 3 kategori altýnda toplanmasý=>
		
				//Arýndýrýlan yorumlar arasýnda ürünün tavsiye edilip-edilmemesinin deðerlendirilmesi...
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
				//Tavsiye bloðunun sonu------------------------------------------------------------------------
				//#-#-#
				//Kalite Deðerlendirmesi
				//Yorumlarda ürün hakkýnda "kaliteli" ve "kalitesiz" olarak kullanýlan kelimelere göre sýnýflandýrýyoruz.
				Vector<String> kalite=new Vector<String>();
				for(int i=0;i<cumleler.size();i++) {
					if(cumleler.get(i).contains("kaliteli")&&!cumleler.get(i).contains("kaliteli deðil")) {
						kalite.add(cumleler.get(i));
					}
				}
				Vector<String> kalitesiz=new Vector<String>();
				for(int i=0;i<cumleler.size();i++) {
					if(cumleler.get(i).contains("kalitesiz")&&!cumleler.get(i).contains("kalitesiz deðil")) {
						kalitesiz.add(cumleler.get(i));
					}
				}
				float toplamkal=kalite.size()+kalitesiz.size();
				float kalYuzde=kalite.size()*100/toplamkal;
				float kalsizYuzde=kalitesiz.size()*100/toplamkal;
				System.out.println("Kaliteli:"+kalYuzde);//9
				System.out.println("Kalitesiz:"+kalsizYuzde);//10
				textArea+="Kaliteli:"+kalYuzde+"\n"+"Kalitesiz:"+kalsizYuzde+"\n";
				//Kalite deðerlendirmesi sonu------------------------------------------------------------------
				//#-#-#
				//Kaç yorumda ürünün fiyat-performans ürünü olduðu hesaplanýyor.
				//Genelde kullanýcýlar "fiyat performans" ya da "fiyat-performans" olarak kullanýyorlar.
				//Bizde bu bilgiyi göz önüne alarak yorumlardan "fiyat performans" geçenleri fp vektöründe depoluyoruz.
				Vector<String> fp=new Vector<String>();
				for(int i=0;i<cumleler.size();i++) {
					if(cumleler.get(i).contains("fiyat-performans")||cumleler.get(i).contains("fiyat performans")) {
						fp.add(cumleler.get(i));
					}
				}
				System.out.println("Fiyat/Performans:"+cumleler.size()+" yorum içerisinde "+fp.size()+" yorum.");//11
				textArea+="Fiyat/Performans:"+fp.size()+"/"+cumleler.size();
				//Kaç yorumda bahsedilmesi / tüm yorumlar olarak yazdýrýyoruz.
				//Fiyat-Performans deðerlendirmesi sonu------------------------------------------------
	}//------------------------------------------------------------------------------------------------
	
	

}
