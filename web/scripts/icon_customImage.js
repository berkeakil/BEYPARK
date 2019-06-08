ymaps.ready(function () {
    var myMap = new ymaps.Map('map', {
            center: [40.988849, 28.777820],
            zoom: 15
        }, {
            searchControlProvider: 'yandex#search'
        }),

        // Creating a content layout.
        MyIconContentLayout = ymaps.templateLayoutFactory.createClass(
            '<div style="color: #FFFFFF; font-weight: bold;">$[properties.iconContent]</div>'
        ),

        myPlacemark = new ymaps.Placemark(myMap.getCenter(), {
            hintContent: 'Cennet Şubemiz',
            balloonContent: "BEYPARK OTOPARK<br></br><img src='../img/beyparklogo.png' style='width:30%'></img>"
        }, {
            /**
             * Options.
             * You must specify this type of layout.
             */
            iconLayout: 'default#image',
            // Custom image for the placemark icon.
            iconImageHref: 'img/titlelogo.png',
            // The size of the placemark.
            iconImageSize: [30, 42],
            /**
             * The offset of the upper left corner of the icon relative
             * to its "tail" (the anchor point).
             */
            iconImageOffset: [-10, -28]
        }),

        myPlacemarkWithContent = new ymaps.Placemark([40.988849, 28.777820], {
            hintContent: 'Cennet Şubemiz',
            balloonContent: "BEYPARK OTOPARK<br></br><img src='../img/beyparklogo.png' style='width:30%'></img>",
            iconContent: ''
        }, {
            /**
             * Options.
             * You must specify this type of layout.
             */
            iconLayout: 'default#imageWithContent',
            // Custom image for the placemark icon.
            iconImageHref: 'img/pin.gif',
            // The size of the placemark.
            iconImageSize: [48, 48],
            /**
             * The offset of the upper left corner of the icon relative
             * to its "tail" (the anchor point).
             */
            iconImageOffset: [-2, -5],
            // Offset of the layer with content relative to the layer with the image.
            iconContentOffset: [15, 15],
            // Content layout.
            iconContentLayout: MyIconContentLayout
        });

    myMap.geoObjects
        .add(myPlacemark)
        .add(myPlacemarkWithContent);
});
