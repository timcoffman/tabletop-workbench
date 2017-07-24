
requirejs.config({
    baseUrl: 'scripts',
    paths: {
        jquery: 'vendor/jquery-3.2.1.min',
        handlebars: 'https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/4.0.10/handlebars.amd.min',
        superagent: 'https://cdnjs.cloudflare.com/ajax/libs/superagent/3.5.2/superagent.min',
    }
});


requirejs(['client-application'], function(app) {
	app.bootstrap(document) ;
});