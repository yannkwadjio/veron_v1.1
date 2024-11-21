const baseURL="/veron"
const paymentMethod=document.querySelector('#paymentMethod');
const receiveAccount=document.querySelector('#receiveAccount');
const refCash=document.querySelector('#refCash');
const refCashValue=document.querySelector('#refCashValue');
const payment=document.querySelector('#payment');
const amount=document.querySelector('#amount');

amount.value=payment.valueAsNumber;


paymentMethod.addEventListener('change',()=>{
if(paymentMethod.value=="ESPECES"){
receiveAccount.innerHTML='';
let option=document.createElement('option');
option.textContent=' Sélectionner une caisse';
option.disabled=true;
option.selected=true;
receiveAccount.append(option);

option=document.createElement('option');
option.textContent=refCashValue.value;
receiveAccount.append(option);


}

})



const refInvoice=document.querySelector('#refInvoice');
const customer=document.querySelector('#customer');
const motif=document.querySelector('#motif');


document.querySelector('#tableInvoice tbody').addEventListener('click', () => {
  const rows = document.querySelectorAll('#tableInvoice tbody tr');
  rows.forEach(row => {
    row.addEventListener('click', function() {
      const refInvoice = row.children[0].innerText;
      async function getInvoiceById() {
        const urlInvoice = `${baseURL}/api/v1/invoices/get-by-ref-Invoice/${refInvoice}`;
        const response = await fetch(urlInvoice, {
                                                   method: 'GET',
                                                   headers: {
                                                       'Content-Type': 'application/json'
                                                   }
                                               });

        if (response.ok) {
          const data = await response.json();


          document.querySelector('#refInvoice').value = data.refInvoice;
          document.querySelector('#customer').value = data.customer;
          document.querySelector('#payment').value = data.rest;
          document.querySelector('#motif').value = data.motif;
if(data.rest>0){
document.querySelector('#btn_payment').click();
}

        }
      }

      getInvoiceById();
    });
  });
});

