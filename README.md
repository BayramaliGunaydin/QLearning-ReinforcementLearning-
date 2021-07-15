# QLearning-ReinforcementLearning-

Bir reinforcement learning algoritması olan qlearning algoritması ile yapılmış bir program.Programın amacı 50x50 rastgele %30 oranında engel içeren bir matriste kullanıcı tarafından verilen başlangıç ve bitiş noktaları arasındaki en kısa mesafeyi bulmak.Ajan çapraz gidebilir.Algoritmayı yazarken greedy-epsilon algoritmasını kullandık.Epsilon oranını %20 belirledik.Bu sayede ajanımız her zaman öğrendiği yolu kullanmıcaktı ve yeni belkide daha kısa bir yol bulabilcekti.Algoritma optimuma yakınsadığında durur ve sonucu ekrana basar.

Kullanıcı ilk önce başlangıç ve bitiş noktası seçer ve başla tuşuna basar.Program kısa sürede en kısa yolu bulur ve atılan adım(episode via step) harcanan maliyet (episode via step) grafiğini yazdırır.
![Qlearning1](https://user-images.githubusercontent.com/76952086/125657068-5bb82e4b-41c2-48fd-b2ab-542bd8849a46.gif)




Reset tuşuna basarak ekranı resetleyebilirsiniz.Noktalar sabit kalır.Tekrar başlatırsanız aynı noktalar üstünden yol aramaya başlar.Farklı bir yol bulunabilir fakar atılan adım sayısı her zaman sabit(minimum) kalıcaktır.
![Qlearning2](https://user-images.githubusercontent.com/76952086/125657072-b08088ca-e161-4216-8dbf-f07d54f9e899.gif)




Algoritmayı yavaşlatıp ajanın nasıl hedefini bulmaya çalıştığını gözlemleyebilirsiniz.
![Qlearning3](https://user-images.githubusercontent.com/76952086/125657073-bb720e4b-bda4-442f-9361-07c06649b6f0.gif)



Daha yakın noktalar arasında yolunu buluşunu görebilirsiniz.
![Qlearning4](https://user-images.githubusercontent.com/76952086/125657675-1a6bc94a-1169-4aeb-9930-64cf241888dc.gif)
