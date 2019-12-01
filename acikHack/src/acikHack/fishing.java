package acikHack;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Vector;

import _zem.io.grpc.internal.TimeProvider;
import _zem.org.antlr.v4.runtime.misc.Pair;



public class fishing {

	public static void main(String[] args) {
		
		ArrayList<String>cumleler = new ArrayList<String>();
		ArrayList<kk> kotuKelimeler = new ArrayList<kk>();
		String line;
		String enKotu="kötü";
		try {
			
		File dosya = new File("C:\\Users\\Muaz DERVENT\\Desktop\\dataset3.txt");////***********girdi tx

		BufferedReader br;
		
			br = new BufferedReader(
					new InputStreamReader(
							new FileInputStream(dosya), "UTF8"));
		

			while ((line = br.readLine()) != null) {
				line=line.substring(11,line.length());
				String yorumlar[]=line.split(" ama | ancak | fakat | oysa | oysaki | kýsacasý ");
				for(int i = 0;i<yorumlar.length;i++) {
					cumleler.add(yorumlar[i]);
				}
				

			}
			
			
			
			for (String cumle: cumleler) {
				if(cumle.contains(enKotu)) {
					String[] kelime =cumle.split(" ");
					
					for(int i =0; i<kelime.length;i++) {
						if(kelime[i]==enKotu)continue;
						else {
							boolean var =false;
							for(kk kk : kotuKelimeler) {
								if(kk.kelime.equals(kelime[i])) {
									kk.sayi+=1;
									var=true;
									break;
								}
							}
							if(!var) {
								kk nKk=new kk();
								nKk.kelime=kelime[i];
								nKk.sayi=1;
								kotuKelimeler.add(nKk);
								}
						}
					}
				}
			}
			for(int i=0;i<kotuKelimeler.size()-1;i++) {
				for(int j=0;j<kotuKelimeler.size()-1-i;j++) {
					kk temp =new kk();
					if(kotuKelimeler.get(j).sayi<kotuKelimeler.get(j+1).sayi) {
						temp.kelime=kotuKelimeler.get(j).kelime;
						temp.sayi=kotuKelimeler.get(j).sayi;
						
						kotuKelimeler.get(j).kelime=kotuKelimeler.get(j+1).kelime;
						kotuKelimeler.get(j).sayi=kotuKelimeler.get(j+1).sayi;
						
						kotuKelimeler.get(j+1).kelime=temp.kelime;
						kotuKelimeler.get(j+1).sayi=temp.sayi;
						
						
					}
				}
			}
			
			for(int i =0;i<50;i++) {
				System.out.println("kotu kelime : "+kotuKelimeler.get(i).kelime+"   sayýsý: "+kotuKelimeler.get(i).sayi);
			}
		
		
		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
