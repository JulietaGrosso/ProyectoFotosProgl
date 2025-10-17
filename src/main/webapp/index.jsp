<html>
<head>
     <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="./Styles.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css" integrity="sha512-SnH5WK+bZxgPHs44uWIX+LLJAJ9/2PkPKZ5QiAj6Ta86w+fsb2TkcmfRyVX3pBnMFcV7oQPJkl9QevSCWr3W6A==" crossorigin="anonymous" referrerpolicy="no-referrer" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Satisfy&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="http://anijs.github.io/lib/anicollection/anicollection.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.1/animate.min.css" integrity="sha512-c42qTSw/wPZ3/5LBzD+Bw5f7bSF2oxou6wEb+I/lqeaKV5FDIfMvvRp772y4jcJLKuGUOpbJMdg/BTl50fJYAw==" crossorigin="anonymous" referrerpolicy="no-referrer" />
       <title>Proyecto de Fotografia</title>
</head>
<body>
   <div class="espacioFrase">
            <h1 data-anijs="if: scroll, on: window, do: animate__slideInLeft animated, before: $scrollReveal repeat">«If you really love nature,<br>
               you will find beauty everywhere»<br>
               -Vincent Van Gogh</h1>

      </div>
      <div class="galeria">
           <h2>Galery</h2>
           <div id="carouselExampleRide" class="carousel slide" data-bs-ride="true">
               <div class="carousel-inner">
                 <div class="carousel-item active">
                   <div class="carrete">
                       <div class="imagenes">
                            <img src="./images/girasoles.jpg" alt="foto1">
                       </div>
                       <div class="imagenes">
                           <img src="./images/donato.JPG" alt="foto1">
                      </div>
                       <div class="imagenes">
                            <img src="./images/musgo.jpg" alt="foto1">
                       </div>
                 </div>
                 </div>
                 <div class="carousel-item">
                   <div class="carrete">
                       <div class="imagenes">
                            <img src="./images/reflejoenelcamino.jpg" alt="foto1">
                       </div>
                       <div class="imagenes">
                           <img src="./images/johnny.JPG" alt="foto1">
                      </div>
                       <div class="imagenes">
                            <img src="./images/puerto.jpg" alt="foto1">
                       </div>
                 </div>
                 </div>
                 <div class="carousel-item">
                   <div class="carrete">
                       <div class="imagenes">
                            <img src="./images/caballo.jpg" alt="foto1">
                       </div>
                       <div class="imagenes">
                           <img src="./images/abeja.jpg" alt="foto1">
                      </div>
                       <div class="imagenes">
                            <img src="./images/pinos.JPG" alt="foto1">
                       </div>
                 </div>
                 </div>
               </div>
               <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleRide" data-bs-slide="prev">
                 <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                 <span class="visually-hidden">Previous</span>
               </button>
               <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleRide" data-bs-slide="next">
                 <span class="carousel-control-next-icon" aria-hidden="true"></span>
                 <span class="visually-hidden">Next</span>
               </button>
             </div>


      </div>

      <div class="sobreMi" id="AboutMe">
           <div class="informacion">
                   <img src="./images/fotomia.jpg" alt="" data-anijs="if: scroll, on: window, do: animate__slideInLeft animated, before: $scrollReveal repeat">
                   <div class="infoDescripcion">
                       <p  data-anijs="if: scroll, on: window, do: animate__fadeInDown animated, before: $scrollReveal repeat">I enjoy capture diferent moments and places in nature, <br>
                           just for fun and to connect with my inner artist.
                       </p>
                       <div class="paraPorfolio" data-anijs="if: scroll, on: window, do: animate__fadeInUp animated, before: $scrollReveal repeat">
                           <h4><i class="fa-solid fa-graduation-cap"></i>Experience:</h4>

                           <ul>
                               <li>2016 technical school graduate. </li>
                               <li>2021 language and literature teacher graduate.</li>
                               <li>At present, I am a student of Software Development. </li>

                           </ul>


                       </div>
                   </div>
           </div>

      </div>
</body>
</html>
