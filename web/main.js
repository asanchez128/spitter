// This script takes care of informing the user of the number of characters that he/she has left.
var writeSpeet = "Compose new Speet...";
var totalCharsAllowed = 145;

$(document).ready(function() {
	
	var $text = $('#speet-text');
	$text.val(writeSpeet);
	
	var $chars = $('.chars-remaining');
	var $speekSubmit = $('#submit-speet');
	$speekSubmit.hide();
	
	// Load initial speets asynchronously
	$.get('speets.jsp', function(data) {
		$('#main').html(data);
	});
		
	// Clear out initial text if user clicks in the textarea
	$text.focus(function() {
	   if ($text.val() == writeSpeet)
		   $text.val('');    
	});          
	
	// Replace default text if nothing was written
	$text.blur(function() {
	   if ($text.val() == '')
		   $text.val(writeSpeet);
	});

	// Not my favorite solution because holding down the key causes
	// the number not to change. keypress event won't work because
	// the value of the textarea doesn't change until that event
	// completes.
	$text.keyup(function() {                    
		$chars.text(totalCharsAllowed - $text.val().length);   
		
		if ($text.val().length > 0)
			$speekSubmit.fadeIn('fast');
		else
			$speekSubmit.fadeOut('fast');
	});
	
	
});