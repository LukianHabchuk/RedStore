function openForm(form, button) {
    if (document.getElementById(form).style.display === 'block'){
        document.getElementById(form).style.display = 'none';
        document.getElementById(button).innerText = "+";
        document.getElementById(button).style.background = '#6CFF95';
    }
    else {
        document.getElementById(form).style.display = 'block';
        document.getElementById(button).innerText = "-";
        document.getElementById(button).style.background = '#ff523b';
    }
}