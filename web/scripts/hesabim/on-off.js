 $(document).ready(function () 
 {
      $('.editBtn').click(function () {
          if ($('.editField').is('[readonly]')) { //checks if it is already on readonly mode
              $('.editField').prop('readonly', false);//turns the readonly off
              $('.editBtn').html('Düzenleme Açık'); //Changes the text of the button
              $('.editBtn').css("background", "green"); //changes the background of the button
              $('.editBtn').css("border", "green"); //changes the border of the button
          } else { //else we do other things
              $('.editField').prop('readonly', true);
              $('.editBtn').html('Düzenleme Kapalı');
              $('.editBtn').css("background", "red");
          }
      });

  });