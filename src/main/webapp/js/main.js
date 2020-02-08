$(document).ready(function(){
  $('#qty_input').prop('disabled', true);
  $('#plus-btn').click(function(){
    $('#qty_input').val(parseInt($('#qty_input').val()) + 1 );
  });
  $('#minus-btn').click(function(){
    $('#qty_input').val(parseInt($('#qty_input').val()) - 1 );
    if ($('#qty_input').val() == 0) {
      $('#qty_input').val(1);
    }

  });
});

$(document).ready(function(){
  $('#qty_input2').prop('disabled', true);
  $('#plus-btn2').click(function(){
    $('#qty_input2').val(parseInt($('#qty_input2').val()) + 1 );
  });
  $('#minus-btn2').click(function(){
    $('#qty_input2').val(parseInt($('#qty_input2').val()) - 1 );
    if ($('#qty_input2').val() == 0) {
      $('#qty_input2').val(1);
    }

  });
});


