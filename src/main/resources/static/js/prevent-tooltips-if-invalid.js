document.addEventListener("DOMContentLoaded", function(event) {
    document.querySelector( "form" )
        .addEventListener( "invalid", function( event ) {
            event.preventDefault();
        }, true );
});