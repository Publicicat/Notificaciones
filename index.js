const express = require('express')
const path = require('path')
const PORT = process.env.PORT || 5000

var bodyParser = require("body-parser")

const admin = require('firebase-admin')
var serviceAccount = require('')
//const { initializeApp } = require('firebase-admin/app')

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
  databaseURL: ''
})
var FCM = require('fcm-push');
const db = admin.database();

//routes


express()
//Settings
  .set('views', path.join(__dirname, 'views'))
  .set('view engine', 'ejs')

//middlewears com handlebars
  .use(express.json())
  //extended: true : rep tot tipus de dades, com imatges, tb
  //extended: false : rep unicament arxius en format json
  .use(express.urlencoded({extended: true}))

//static files
  .use(express.static(path.join(__dirname, 'public')))

  .get('/', (req, res) => {
    db.ref('likes').once('value', (snapshot) => {
      const data = snapshot.val();
      res.render('pages/index', {likes: data});
    })
  })

  //POST
  //https://desolate-brook-64078.herokuapp.com/token-device
  //token
  //Per desserialitzar, a partir d'aquí necessitem la llibreria Body-parser
  //"/token-device" és també la ruta que definirem a l'aplicació android, ios
  //o html -form action: "/token-device" method="POST"
  //THANKS TO Fatz Code: https://www.youtube.com/watch?v=b6KJ7FSMifw
  .post("/token-device", function(req, res){
    console.log(req.body);
    //Prepare data to be recorded
    const newGato = {
      gato: req.body.yogato,
      token: req.body.token
    }
    const newLikeTo = {
      gato: req.body.otrogato
    }
    //Check if users exists to record new user and like-tap or just the like-tap
    var path = "";
    var userExists = "";
    var tokenGatoDestino = "";
    db.ref('gatos').once('value', (snapshot) => {
      snapshot.forEach(function(childSnapshot){
        var data = childSnapshot.val();
        if (data.gato == req.body.yogato){
          //user already exists
          userExists = "yes";
        }
        //Get the token from gato that input data, even if it is a new gato user
        if (data.gato == req.body.otrogato){
          tokenGatoDestino = data.token;
        }
      })
      if (userExists == "yes" ) {
        //user already exists
        var urlRouteToFb = db.ref('likes').push(newLikeTo);
        var path = urlRouteToFb.toString(); //https://https://petagramcuatro-default-rtdb.europe-west1.firebasedatabase.app/usuarios/-KJlTaOQPwP-ssImryV1
      } else {
        //record new user checked by its token
        db.ref('gatos').push(newGato);
        var urlRouteToFb = db.ref('likes').push(newLikeTo);
        var path = urlRouteToFb.toString(); //https://https://petagramcuatro-default-rtdb.europe-west1.firebasedatabase.app/usuarios/-KJlTaOQPwP-ssImryV1
      }

      var pathSplit = path.split('likes/'); //Partim la string en dos [0] i [1] a partir del character/s clau
      var tokenDevices = req.body;
      var idAutoGenerado = pathSplit[1];

      var respuesta = {};
      var like = "";
      var key = "";
      var ref = db.ref('likes');//Hem posat el text en lloc de la variable

      //snapshot, prevChildKey
      console.log(req.params.id);
      var serverKey = '';
      var fcm = new FCM(serverKey);
      //var db = admin.database(); NO CAL PASSAR-LA COM A VARIABLE
      //req.send('avisado');

      //FROM here CUSTOM
      var id = idAutoGenerado;
      var gato = req.params.otrogato;
      var ref = db.ref('likes/' + id); // = var ref = db.ref("token-device/" + id);

      var cualGato = "";
      var respuesta = {};
      //To read and write there is no more node js code in docs, but
      //java files to configure realtime database comunication form android to db
      ref.once('value', function (snapshot){
        console.log(snapshot.val());
        cualGato = snapshot.val();
        var mensaje = req.body.yogato + " te dió un tap!";
        var tokenDestinatario = cualGato.token; //La primera capa de key-value pair
        var gatoDestinatario = cualGato.gato;

        //Start FUNCTION (npm module fcm-push)
        var message = {
          to: tokenGatoDestino, // required fill with device token or topics
          collapse_key: '',
          data: {
            id: id,
            gato: gato
          },
          //Notificacion opciones: https://firebase.google.com/docs/cloud-messaging/http-server-ref
            notification: {
                title: 'Notification from server!?',
                body: mensaje,
                icon: 'ic_baseline_3p_24',
                sound: 'default',
                color: '#00BCD4'
              }
          };
          //callback style
          fcm.send(message, function(err, response){
              if (err) {
                  console.log("Something has gone wrong!");
              } else {
                  console.log("Successfully sent with response: ", response);
              }
          });
          //End FUNCTION
          respuesta = {
            id: id,
            token: cualGato.token,
            gato: cualGato.gato
          };
          res.setHeader("Content-Type", "application/json");
          res.send(JSON.stringify(respuesta));

        //End function(snapshot)
        }, function(errorObject) {
          respuesta = {
            id: "",
            token: "",
            gato: ""
          };
          res.setHeader("Content-Type", "application/json");
          res.send(JSON.stringify(respuesta));
          //res.end();//wss
          console.log("The read failed: " + errorObject.code);
        });
    })
    //res.redirect('tap-pic/' + idAutoGenerado + '/' + usuario.gato );
    //res.send(req.body.token); Perquè actualitzi sense refrescar: res.send('/');
                           // Això si tenim ben concatenats els mètodes get & post i via html
  }) //end POST


  //GET
  //https://desolate-brook-64078.herokuapp.com/"tap-pic/{id}/{gato}/"
  //id
  //gato
  .get('/token-device/tap-pic/:id/:otrogato', function(req, res){
    console.log(req.params.id);
    var serverKey = 'AAAAvJ_gLsM:APA91bFphJGxOZC0iDPze6wVzE64UQ9fvNwUSGpKy351JjJd1NlHll-E69tOqDV8H_LEotwqhm_fgdw2k62uI2VW8t96T4PG8CePHqMPFY_L3j5y2fcJejhdg1s1uT4rWRdOgXsbWNoo';
    var fcm = new FCM(serverKey);
    //var db = admin.database(); NO CAL PASSAR-LA COM A VARIABLE
    //req.send('avisado');

    //FROM here CUSTOM
    var id = req.params.id;
    var gato = req.params.otrogato;
    var ref = db.ref('likes/' + id); // = var ref = db.ref("token-device/" + id);

    var cualGato = "";
    var respuesta = {};
    //To read and write there is no more node js code in docs, but
    //java files to configure realtime database comunication form android to db
    ref.once('value', function (snapshot){
      console.log(snapshot.val());
      cualGato = snapshot.val();
      var mensaje = gato + " te dió un tap!";
      var tokenDestinatario = cualGato.token; //La primera capa de key-value pair
      var gatoDestinatario = cualGato.gato;

      //Start FUNCTION (npm module fcm-push)
      var message = {
        to: tokenDestinatario, // required fill with device token or topics
        collapse_key: '',
        data: {
          id: id,
          gato: gato
        },
        //Notificacion opciones: https://firebase.google.com/docs/cloud-messaging/http-server-ref
          notification: {
              title: 'Notification from server!?',
              body: mensaje,
              icon: 'ic_baseline_3p_24',
              sound: 'default',
              color: '#00BCD4'
            }
        };
        //callback style
        fcm.send(message, function(err, response){
            if (err) {
                console.log("Something has gone wrong!");
            } else {
                console.log("Successfully sent with response: ", response);
            }
        });
        //End FUNCTION
        respuesta = {
          id: id,
          token: cualGato.token,
          gato: cualGato.gato
        };
        res.setHeader("Content-Type", "application/json");
        res.send(JSON.stringify(respuesta));

      //End function(snapshot)
      }, function(errorObject) {
        respuesta = {
          id: "",
          token: "",
          gato: ""
        };
        res.setHeader("Content-Type", "application/json");
        res.send(JSON.stringify(respuesta));
        //res.end();//wss
        console.log("The read failed: " + errorObject.code);
      });

      /*
      //callback style
      fcm.send(message, function(err, response){
          if (err) {
              console.log("Something has gone wrong!");
          } else {
              console.log("Successfully sent with response: ", response);
          }
      });
      */
      //To here CUSTOM

  })//end GET

  .listen(PORT, () => console.log(`Listening on ${ PORT }`))
