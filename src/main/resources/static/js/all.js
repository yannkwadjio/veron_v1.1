setInterval(() => {
    const toastAlert = document.querySelector('#toast_alert');


    if (toastAlert.style.display !== 'none') {

        toastAlert.style.display = 'none';
    }
}, 3000);


var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'))
var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
  return new bootstrap.Tooltip(tooltipTriggerEl)
})




