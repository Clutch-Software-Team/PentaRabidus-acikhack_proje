# PentaRabidus-acikhack_proje

 *Erguvan, beş kişilik Penta Rabidus takımının, 24 saat süren acikhack.com hackaton katılımında, Türkçe Doğal Dil İşleme dalında, java ile [zemberek kütüphanesi](https://github.com/ahmetaa/zemberek-nlp "zemberek kütüphanesi") kullanılarak inşa edilmiştir.*

# Erguvan nedir ?

 Erguvan, günümüz e-ticaret sitelerinde bulunan kullanıcının benmerkezli yorumlarını egale eden,
yalnızca ürüne odaklı yorumları alıcıya gösteren ve en beğenilen yorumlardan oluşan bir özeti ve ürünün temel özelliklerini yansıtan istatiski verileri alıcıya sunan bir Türkçe Doğal Dil İşleme programıdır.

# Adım Adım Erguvan

**Adım 1 - Veri Seti Oluşturma**

 Günümüz popüler e-ticaret sitelerinden birinin **klavye** kategorisindeki yorumları bir [review scraper](https://github.com/0x01h/hepsiburada-review-scraper "review scraper") yazılımını, ayrıca bu yazılım yarışma dökümantasyonunda da önerilmiştir, kullanarak 1500 yorum içeren bir dataset elde ettik.

**Adım 2-Ana dataset içerisinde ki yorumları normalizasyon işleminden*** **geçirildi.**

 *Zemberek kütüphanesinden TurkishSentenceNormalizer() fonksiyonu kullanıldı.

**Adım 3-Ana dataset içerisinde tek yorumda hem ürün ile ilişkili hem de ürün ile ilişkisiz cümleleri ayırmak için Türkçe'de bulunan bağlaçları*** **kullanarak yorumları birden çok cümleye ayırma işlemi uygulandı.**

 *Bağlaç kelimeleri; Ama, ancak, fakat, oysa, oysa ki, kısacası.

**Adım 4-Cümlelere ayırma işleminden sonra yorumlar içerisinde direkt olarak ürün ile alakalı ve alakasız yorum olacak şekilde ayırma(süzgeç) işlemi yapılmıştır.***

 *Süzgeç işleminde iki farklı dizi kullanılmıştır. İlk dizi ürün ile direkt olarak
   alakalı(alakaliOzelliklerSuzgec), ikinci dizi ise ürün ile direkt olarak alakasız
   kelimeler(istenmeyenOzelliklerSuzgec) olarak seçilmiştir.**

  **alakaliOzelliklerSuzgec ve istenmeyenOzelliklerSuzgec dizisi kelimelerini seçme işlemi ana dataset içerisinde bulunan kelimelerin frekansına bakılarak seçilmiştir.(datasetFrekans.txt)

 *Ana dataset üzerinden alakaliOzelliklerSuzgec* dizisi kullanıldıktan sonra istenmeyenOzelliklerSuzgec* dizisi kullanılarak tekrardan bir süzgeç işlemi** yapıldı.

  **İşlem sonrasında oluşan datasetler alakaliOzelliklerSuzgec.txt ve istenmeyenOzelliklerSuzgec.txt içerisindedir.

**Adım 5-Tüm süzgeç işlemlerinden sonra saf kalan dataset(istenmeyenOzelliklerSuzgec.txt) üzerinde her cümleyi ürün hakkında olumlu&olumsuz şeklinde etiketlendi.***

 *Etiketleme işleminde kotuKelimler.txt ve iyiKelimeler.txt datasetleri kullanıldı.

 *Kullanılan dataset kelimelerine bir kat sayı atandı. İyi kelimeler için kullanılan kat sayı 1.333, kötü kelimeler için 0.666 olarak belirlendi. Ele alınan yorum içerisinde iyi ve kötü kelimeler tespit edilerek her birinin kat sayıları çarpıldı. Çıkan sonucun 1'den büyük olmasıyla cümle olumlu, 1'den küçük olmasıyla cümle olumsuz sıfatlarıyla etiketlendi.

**Adım 6-Ana dataset, istenmeyenOzelliklerSuzgec.txt ve etiketleme işleminden sonra her dataset için olumlu&olumsuz etiketi gerçekleştirilip. Belli bir istatistik elde edilmiştir.***

 *Ana dataset ve istenmeyenOzelliklerSuzgec.txt üzerindeki olumlu&olumsuz etiketlemesi dataset çekme işlemiyle gelen kullanıcı yıldızlarından
   oluşturulmuştur.

**ÇIKARIM: Elde edilen istatistiklerde ana dataset sonucu ile son etiketleme işleminden sonra ki değerler arasında fark oluştu. Bu farkın kullanıcıların yazmış olduğu yorumlar karşısında kullandıkları yıldızların yetersiz olduğu kanısına varıldı. 5 yıldız yerine 10 basamaklı yıldız kullanılabilirdi.**

**Adım 7-İstatistiklerden sonra istenmeyenOzelliklerSuzgec.txt dataset içerisinden ürünün genel tanıtımı ile ilgili spesifik kelimeler*** **kullanılarak yorumlar içerisinde genel bir özet elde edildi.**

  *Tavsiye ederim, tavsiye edilir, tavsiye ediyorum, tavsiye etmem, tavsiye edilmez vb.

**Adım 8-Anlamlı istatistikler son olarak arayüz ile servis edildi.**

**!Kod içerisindeki yorum satırları ile programın kullanımı açıklanmıştır**



