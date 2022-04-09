# AndroidCourse   
Catch Sasuke:  
Bu projede son güncellemeler ile gelen Binding jetpack öğesini kullanarak küçük bir oyun yapmaya çalıştım. Runnable classından oluşturduğum obje ile arka tarafta sürekli çalışan
bir süreç başlattım. Süreç şöyle başlıyor: ekranda bir sürü Sasuke imageları var ve bunları bir diziye atıp dizideki bütün elemanları görünmez yapıyoruz. Daha sonra
random objesi ile diziden random bir index seçip onu görünür yaparız ve süreci sürekli tekrarlarız. Böylece listedeki bütün elemanlar gizlenir ve sonra bir tanesi görünür olur.
Sasuke kaçar yani :). Özetle Runnable, Handler, AlertDialog, Intent gibi kavramları pratik yaptığım bir proje oldu.  

ArtBook:  
Bu projede telefonumuzda bulunan bir sanat görselini  uygulmamamıza kaydediyoruz. Ve eklediğimiz şeyleri veri tabanına (SQLDatabase) kaydederek bunu sağlıyoruz. İzin alma
yönetimini pratik ediyoruz. Kullanıcıdan izin aldıktan sonra görseli database'e kaydediyoruz. ekranda ise sanat eserinin ismini ve id'sini listeliyoruz.
Ekrana eklediğimiz şeyler ise Recyclerview ile listeleniyor. Böylece en verimli listeleme yöntemini, hafızada yer sadece ekrandaki viewler kadar yer tutarak kullanıyoruz.
Ve tabiki aktivite yönetimini de sağlıyoruz. Aktiviler arasındaki geçişi sağlamak ve izinleri alabilmek için Launcherlar oluşturuyoruz. Bu launcherlar
sayesinde işimiz daha kolaylaşıyor. Bu projede SQLDatabase, Recyclerview gibi çok önemli konuları pratik yapmış olduk.
