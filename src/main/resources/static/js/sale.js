const baseURL="/veron"
const customer=document.querySelector('#customer');
const services=document.querySelector('#service');
const selectProduct=document.querySelector("#select_product");
const stockFinal=document.querySelector('#stockFinal');
const totalPrice=document.querySelector('#totalPrice');
let totalPriceValue=document.querySelector('#totalPriceValue');
const pt=document.querySelector('#pt');
const sellingPrice=document.querySelector('#sellingPrice');
const qte=document.querySelector('#qte');
const ps=document.querySelector('#ps');
const btnAjout=document.querySelector('#btn_ajout');
const tableBody=document.querySelector('#tableSaleTemp tbody');
const paymentMethod=document.querySelector("#paymentMethod");
const paymentMethodValue=document.querySelector("#paymentMethodValue");
const customerValue=document.querySelector("#customerValue");
const serviceValue=document.querySelector("#serviceValue");
const remise=document.querySelector('#remise');
const remiseValue=document.querySelector('#remiseValue');
const tableSale=document.querySelector('#tableSale');
const refCash=document.querySelector('#refCash');
const refCashValue=document.querySelector('#refCashValue');
const tva=document.querySelector('#tva');
const totalTva=document.querySelector('#totalTva');
const prixHT=document.querySelector('#prixHT');
const productName=document.querySelector('#productName');
const refStore=document.querySelector('#refStore');
const store=document.querySelector('#store');


store.value=refStore.textContent;

refCashValue.value=refCash.innerText;
let productIndex = 0;

paymentMethod.addEventListener('change',()=>{paymentMethodValue.value=paymentMethod.value});
customer.addEventListener('change',()=>{customerValue.value=customer.value});
services.addEventListener('change',()=>{serviceValue.value=services.value});

stockFinal.setAttribute('readonly',true);
sellingPrice.setAttribute('readonly',true);




async function getAllCustomer(){
const urlCustomer=`${baseURL}/api/v1/customers/get-all`;
const response=await fetch(urlCustomer, {
                                          method: 'GET',
                                          headers: {
                                              'Content-Type': 'application/json'
                                          }
                                      });
if(!response.ok){}else{
const data=await response.json();
customer.innerHTML='';
let option=document.createElement('option');
option.textContent='Selectionner un client';
option.selected=true;
option.disabled=true;
customer.append(option);

data.forEach(custom=>{
option=document.createElement('option');
option.textContent=custom.fullName;
option.value=custom.fullName;
customer.append(option);
})
let choicesCustomer = new Choices(customer);

}}
getAllCustomer();


async function getAllServices(){
const urlService=`${baseURL}/api/v1/selling-services-category/get-all`;
const response=await fetch(urlService, {
                                         method: 'GET',
                                         headers: {
                                             'Content-Type': 'application/json'
                                         }
                                     });
if(!response.ok){}else{
const data=await response.json();
services.innerHTML='';
let option=document.createElement('option');
option.textContent='Selectionner un service';
option.selected=true;
option.disabled=true;
services.append(option);
let listProducts=new Set();
data.forEach(service=>{
if(!listProducts.has(service.name)){
option=document.createElement('option');
option.textContent=service.name;
option.value=service.name;
services.append(option);
listProducts.add(service.name)
}
})
let choicesServices = new Choices(services);
}
}
getAllServices();




let choicesProduct1;

services.addEventListener('change', () => {
    if (choicesProduct1) {
        choicesProduct1.destroy();
    }
   sellingPrice.value=0;
    if (services.value == 'VENTES') {

        productName.textContent = "Produits *";
        selectProduct.disabled = false;
        lot.disabled = false;
       async function getProductByCategory(){
       const urlProductByCategory=`${baseURL}/api/v1/products-stock/get-all`;
       const response=await fetch(urlProductByCategory, {
                                                          method: 'GET',
                                                          headers: {
                                                              'Content-Type': 'application/json'
                                                          }
                                                      });
       if(!response.ok){

       }else{
       const data=await response.json();
       selectProduct.innerHTML='';
       let optionProduct=document.createElement('option');
       optionProduct.textContent='Selectionner un produit';
       optionProduct.value='Selectionner un produit';
       optionProduct.selected=true;
       optionProduct.disabled=true;
       selectProduct.append(optionProduct);
       let productList=new Set();
       data.forEach(product=>{
       if(product.category=="PRODUITS" && product.store==refStore.textContent && !productList.has(product.name)){
       optionProduct=document.createElement('option');
       optionProduct.textContent=product.name;
       optionProduct.value=product.name;
       selectProduct.append(optionProduct);
       productList.add(product.name);
       }
       })
       choicesProduct1 = new Choices(selectProduct);
       }
       }
       getProductByCategory();

        qte.textContent = "Qté *";
        quantity.placeholder = 'Quantité';
        quantity.type = 'number';
        ps.textContent = 'Prix de vente';
        pt.textContent = 'Prix total';
        totalPrice.readOnly = true;
    } else {
        lot.disabled = true;
        productName.textContent = "Sous-services *";

        async function getServicesByName() {
            const urlService = `${baseURL}/api/v1/selling-services/get-all`;
            const response = await fetch(urlService, {
                                                       method: 'GET',
                                                       headers: {
                                                           'Content-Type': 'application/json'
                                                       }
                                                   });
            if (!response.ok) {
                // Gérer les erreurs ici
            } else {
                const data = await response.json();
                selectProduct.innerHTML = '';
                let option = document.createElement('option');
                option.textContent = 'Selectionner un sous-service';
                option.value = 'Selectionner un sous-service';
                option.selected = true;
                option.disabled = true;
                selectProduct.append(option);

                data.forEach(sellingService => {
                    if (sellingService.category == services.value) {
                        option = document.createElement('option');
                        option.textContent = sellingService.name;
                        option.value = sellingService.name;
                        selectProduct.append(option);
                    }
                });

                choicesProduct1 = new Choices(selectProduct);
            }
        }

        getServicesByName();

        qte.textContent = "Motif *";
        quantity.placeholder = 'Entrer le motif';
        quantity.type = 'textarea';
        ps.textContent = 'Prix du service';
        pt.textContent = 'Montant versé';
        totalPrice.readOnly = false;
        totalPrice.placeholder = 'Montant versé';
    }
});


selectProduct.addEventListener('change',()=>{
if(service.value=="VENTES"){
async function getByProduct(){
const urlProduct=`${baseURL}/api/v1/products/get-by-name/${selectProduct.value}`;
const response=await fetch(urlProduct, {
                                         method: 'GET',
                                         headers: {
                                             'Content-Type': 'application/json'
                                         }
                                     });
if(!response.ok){
}else{
const data=await response.json();
sellingPrice.value=data.sellingPrice;
stockFinal.value=data.finalStock;
sellingPrice.setAttribute('value',data.sellingPrice);
stockFinal.setAttribute('value',data.finalStock);
sellingPrice.setAttribute('readonly', true);
stockFinal.setAttribute('readonly', true);
}
}
getByProduct();
}else{
 async function getServicesByName(){
 const urlService=`${baseURL}/api/v1/selling-services/get-by-name/${selectProduct.value}`;
 const response=await fetch(urlService, {
                                          method: 'GET',
                                          headers: {
                                              'Content-Type': 'application/json'
                                          }
                                      });
 if(!response.ok){}else{
 const data=await response.json();
 sellingPrice.value=data.price;
 quantity.value=data.description;
 }
 }
 getServicesByName();
 }

})



let choicesLot;

selectProduct.addEventListener('change',()=>{
if(service.value=="VENTES"){
   if (choicesLot) {
        choicesLot.destroy();
    }
async function getByProductStock(){
const urlProduct=`${baseURL}/api/v1/products-stock/get-all`;
const response=await fetch(urlProduct, {
                                         method: 'GET',
                                         headers: {
                                             'Content-Type': 'application/json'
                                         }
                                     });
if(!response.ok){
}else{
const data=await response.json();
lot.innerHTML='';
let option=document.createElement('option');
option.textContent='Sélectionner un lot';
option.value='Sélectionner un lot';
option.selected=true;
option.disabled=true;
lot.append(option);

data.forEach(product=>{
if(product.store==refStore.textContent && product.name==selectProduct.value){
option=document.createElement('option');
option.textContent=product.lot;
option.value=product.lot;
lot.append(option);
}
})
choicesLot= new Choices(lot);
}
}
getByProductStock();
}
})



lot.addEventListener('change',()=>{
if(service.value=="VENTES"){
async function getByStock(){
const urlProduct=`${baseURL}/api/v1/products-stock/get-all`;
const response=await fetch(urlProduct, {
                                         method: 'GET',
                                         headers: {
                                             'Content-Type': 'application/json'
                                         }
                                     });
if(!response.ok){
}else{
const data=await response.json();
let total=0;
data.forEach(product=>{
if(product.store==refStore.textContent && product.name==selectProduct.value && product.lot==lot.value){
total+=product.finalStock;
stockFinal.value=total;
}
})
}
}
getByStock();
}
})

document.querySelector('#quantity').addEventListener('keyup',()=>{
if(services.value=='VENTES'){
totalPrice.value=Number(quantity.value) * Number(sellingPrice.value);
totalPrice.setAttribute('value',Number(quantity.value) * Number(sellingPrice.value));
}

})

quantity.valueAsNumber=0;

btnAjout.addEventListener('click',()=>{
let totalQuantity=0;
const rows=document.querySelectorAll('#tableSaleTemp tbody tr');
rows.forEach(row=>{
if(row.children[2].innerText==selectProduct.value){
totalQuantity+=Number(row.children[3].innerText);
}
})
if(Number(quantity.value)+totalQuantity<=Number(stockFinal.value) && service.value=='VENTES'){
function ajoutProduct(){
    document.querySelector('form').addEventListener('submit',(event)=>event.preventDefault())
    tva.disabled=true;
    if(paymentMethod.value!=''){
     if(services.value!=' Selectionner un service'){
      if(customer.value!=' Selectionner un client'){
       if(services.value=='VENTES'){
       if(selectProduct.value!=' Sélectionner un produit'){
       if(lot.value!='Sélectionner un lot'){
        if(quantity.valueAsNumber<=stockFinal.valueAsNumber && quantity.valueAsNumber!=0){
        if(paymentMethod.value=="A_CREDIT" && customer.value!="CLIENT INCONNU"){
         let table=document.querySelector('table');
                  let numberRow=table.rows.length-1;
                  let tableRow=document.createElement('tr');
                  let tableData=document.createElement('td');
                  tableData.textContent=numberRow;
                  tableRow.append(tableData);

                     tableData=document.createElement('td');
                   tableData.textContent=lot.value;
                   tableRow.append(tableData);

                  tableData=document.createElement('td');
                  tableData.textContent=selectProduct.value;
                   tableRow.append(tableData);

                   tableData=document.createElement('td');
                   tableData.textContent=quantity.value;
                   tableRow.append(tableData);

                   tableData=document.createElement('td');
                   tableData.textContent=sellingPrice.value;
                   tableRow.append(tableData);

                   tableData=document.createElement('td');
                   tableData.textContent=totalPrice.value;
                   tableRow.append(tableData);

                   tableData=document.createElement('td');
                   tableData.innerHTML="<button class='"+numberRow+"' style='border:none'><i class='fa-solid fa-trash-can'></i></button>";
                   tableRow.append(tableData);

                   tableData=document.createElement('td');
                   tableData.textContent='VENTES';
                   tableData.className='d-none';
                   tableRow.append(tableData);

                   tableBody.append(tableRow);

                   let total=0;
                   let tvaValue=0;
                   const rows = document.querySelectorAll('#tableSaleTemp tbody tr');
                   rows.forEach(row => {
                     total=total+Number(row.childNodes[5].innerHTML);
                     if(tva.checked==true){
                     tvaValue=tvaValue+(Number(row.childNodes[5].innerHTML)*0.1925);
                     }
                     })

                   priceTotal.textContent=Math.round(total-tvaValue-remise.value);
                    totalTva.textContent=Math.round(Number(tvaValue));
                   totalPriceValue.value=Math.round(total-tvaValue-remise.value);


                  remiseValue.value=remise.value;
                  tvaValues.value=Math.round(tvaValue);
                  prixHT.textContent=total;
                  quantity.value=0
                  totalPrice.value=0


        }else if(paymentMethod.value=="ESPECES"){
         let table=document.querySelector('table');
                  let numberRow=table.rows.length-1;
                  let tableRow=document.createElement('tr');
                  let tableData=document.createElement('td');
                  tableData.textContent=numberRow;
                  tableRow.append(tableData);
                   tableData=document.createElement('td');
                   tableData.textContent=lot.value;
                   tableRow.append(tableData);

                  tableData=document.createElement('td');
                  tableData.textContent=selectProduct.value;
                   tableRow.append(tableData);

                   tableData=document.createElement('td');
                   tableData.textContent=quantity.value;
                   tableRow.append(tableData);

                   tableData=document.createElement('td');
                   tableData.textContent=sellingPrice.value;
                   tableRow.append(tableData);

                   tableData=document.createElement('td');
                   tableData.textContent=totalPrice.value;
                   tableRow.append(tableData);

                   tableData=document.createElement('td');
                   tableData.innerHTML="<button class='"+numberRow+"' style='border:none'><i class='fa-solid fa-trash-can'></i></button>";
                   tableRow.append(tableData);

                    tableData=document.createElement('td');
                    tableData.textContent='VENTES';
                    tableData.className='d-none';
                    tableRow.append(tableData);

                   tableBody.append(tableRow);

                   let total=0;
                   let tvaValue=0;
                   const rows = document.querySelectorAll('#tableSaleTemp tbody tr');
                   rows.forEach(row => {
                     total=total+Number(row.childNodes[5].innerHTML);
                     if(tva.checked==true){
                     tvaValue=tvaValue+(Number(row.childNodes[5].innerHTML)*0.1925);
                     }
                     })

                   priceTotal.textContent=Math.round(total-tvaValue-remise.value);
                    totalTva.textContent=Math.round(Number(tvaValue));
                   totalPriceValue.value=Math.round(total-tvaValue-remise.value);

                  remiseValue.value=remise.value;
                  tvaValues.value=Math.round(tvaValue);
                  prixHT.textContent=total;
                  quantity.value=0
                  totalPrice.value=0

        }else{alert('Moyen de paiement incorrect')}


           }else{alert('Quantité non renseignée ou insuffisante');}
           }else{alert('Lot non sélectionné');}
           }else{alert('Produit non selectionné');
           }
    }else if(services.value!='VENTES'){
 if(paymentMethod.value=="A_CREDIT" && customer.value!="CLIENT INCONNU"){

      let table=document.querySelector('table');
      let numberRow=table.rows.length-1;
      let tableRow=document.createElement('tr');

      let tableData=document.createElement('td');
      tableData.textContent=numberRow;
      tableRow.append(tableData);

       tableData=document.createElement('td');
       tableData.textContent=services.value;
      tableRow.append(tableData);

      tableData=document.createElement('td');
      tableData.textContent=quantity.value;
       tableRow.append(tableData);

       tableData=document.createElement('td');
       tableData.textContent=1;
       tableRow.append(tableData);

       tableData=document.createElement('td');
       tableData.textContent=totalPrice.value;
       tableRow.append(tableData);

       tableData=document.createElement('td');
       tableData.textContent=totalPrice.value;
       tableRow.append(tableData);

       tableData=document.createElement('td');
       tableData.innerHTML="<button class='"+numberRow+"' style='border:none'><i class='fa-solid fa-trash-can'></i></button>";
       tableRow.append(tableData);

        tableData=document.createElement('td');
        tableData.textContent='SERVICES';
        tableData.className='d-none';
        tableRow.append(tableData);

       tableBody.append(tableRow);

       let total=0;
       let tvaValue=0;
       const rows = document.querySelectorAll('#tableSaleTemp tbody tr');
       rows.forEach(row => {
         total=total+Number(row.childNodes[5].innerHTML);
         if(tva.checked==true){
         tvaValue=tvaValue+(Number(row.childNodes[5].innerHTML)*0.1925);
         }
         })

       priceTotal.textContent=Math.round(total-tvaValue-remise.value);
        totalTva.textContent=Math.round(Number(tvaValue));
       totalPriceValue.value=Math.round(total-tvaValue-remise.value);

      remiseValue.value=remise.value;
      tvaValues.value=Math.round(tvaValue);
      prixHT.textContent=total;
      totalPrice.value=0;

}else if(paymentMethod.value=="ESPECES"){

      let table=document.querySelector('table');
      let numberRow=table.rows.length-1;
      let tableRow=document.createElement('tr');
      let tableData=document.createElement('td');
      tableData.textContent=numberRow;
      tableRow.append(tableData);

      tableData=document.createElement('td');
      tableData.textContent=services.value;
      tableRow.append(tableData);

      tableData=document.createElement('td');
      tableData.textContent=quantity.value;
       tableRow.append(tableData);

       tableData=document.createElement('td');
       tableData.textContent=1;
       tableRow.append(tableData);

       tableData=document.createElement('td');
       tableData.textContent=totalPrice.value;
       tableRow.append(tableData);

       tableData=document.createElement('td');
       tableData.textContent=totalPrice.value;
       tableRow.append(tableData);

       tableData=document.createElement('td');
       tableData.innerHTML="<button class='"+numberRow+"' style='border:none'><i class='fa-solid fa-trash-can'></i></button>";
       tableRow.append(tableData);

       tableData=document.createElement('td');
       tableData.textContent='SERVICES';
       tableData.className='d-none';
       tableRow.append(tableData);

       tableBody.append(tableRow);

       let total=0;
       let tvaValue=0;
       const rows = document.querySelectorAll('#tableSaleTemp tbody tr');
       rows.forEach(row => {
         total=total+Number(row.childNodes[5].innerHTML);
         if(tva.checked==true){
         tvaValue=tvaValue+(Number(row.childNodes[5].innerHTML)*0.1925);
         }
         })

       priceTotal.textContent=Math.round(total-tvaValue-remise.value);
        totalTva.textContent=Math.round(Number(tvaValue));
       totalPriceValue.value=Math.round(total-tvaValue-remise.value);

      remiseValue.value=remise.value;
      tvaValues.value=Math.round(tvaValue);
      prixHT.textContent=total;
       totalPrice.value=0;

}else{alert('Moyen de paiement incorrect')};


   } else{alert('Produit non selectionné')};

    }else{
             alert('Client non selectionné');
             }
    }else{
         alert('Service non selectionné');
         }
    }  else{
         alert('Mode de paiement non selectionné');
         }}
    ajoutProduct();
}else if(service.value!='VENTES'){
      function ajoutProduct(){
          document.querySelector('form').addEventListener('submit',(event)=>event.preventDefault())
          tva.disabled=true;
          if(paymentMethod.value!=''){
           if(services.value!=' Selectionner un service'){
            if(customer.value!=' Selectionner un client'){
             if(services.value=='VENTES'){
             if(selectProduct.value!=' Sélectionner un produit'){
             if(lot.value!='Sélectionner un lot'){
              if(quantity.valueAsNumber<=stockFinal.valueAsNumber && quantity.valueAsNumber!=0){
              if(paymentMethod.value=="A_CREDIT" && customer.value!="CLIENT INCONNU"){
               let table=document.querySelector('table');
                        let numberRow=table.rows.length-1;
                        let tableRow=document.createElement('tr');
                        let tableData=document.createElement('td');
                        tableData.textContent=numberRow;
                        tableRow.append(tableData);

                           tableData=document.createElement('td');
                         tableData.textContent=lot.value;
                         tableRow.append(tableData);

                        tableData=document.createElement('td');
                        tableData.textContent=selectProduct.value;
                         tableRow.append(tableData);

                         tableData=document.createElement('td');
                         tableData.textContent=quantity.value;
                         tableRow.append(tableData);

                         tableData=document.createElement('td');
                         tableData.textContent=sellingPrice.value;
                         tableRow.append(tableData);

                         tableData=document.createElement('td');
                         tableData.textContent=totalPrice.value;
                         tableRow.append(tableData);

                         tableData=document.createElement('td');
                         tableData.innerHTML="<button class='"+numberRow+"' style='border:none'><i class='fa-solid fa-trash-can'></i></button>";
                         tableRow.append(tableData);

                         tableData=document.createElement('td');
                         tableData.textContent='VENTES';
                         tableData.className='d-none';
                         tableRow.append(tableData);

                         tableBody.append(tableRow);

                         let total=0;
                         let tvaValue=0;
                         const rows = document.querySelectorAll('#tableSaleTemp tbody tr');
                         rows.forEach(row => {
                           total=total+Number(row.childNodes[5].innerHTML);
                           if(tva.checked==true){
                           tvaValue=tvaValue+(Number(row.childNodes[5].innerHTML)*0.1925);
                           }
                           })

                         priceTotal.textContent=Math.round(total-tvaValue-remise.value);
                          totalTva.textContent=Math.round(Number(tvaValue));
                         totalPriceValue.value=Math.round(total-tvaValue-remise.value);
                          totalPrice.value=0;


                        remiseValue.value=remise.value;
                        tvaValues.value=Math.round(tvaValue);
                        prixHT.textContent=total;
                        totalPrice.value=0

              }else if(paymentMethod.value=="ESPECES"){
               let table=document.querySelector('table');
                        let numberRow=table.rows.length-1;
                        let tableRow=document.createElement('tr');
                        let tableData=document.createElement('td');
                        tableData.textContent=numberRow;
                        tableRow.append(tableData);
                         tableData=document.createElement('td');
                         tableData.textContent=lot.value;
                         tableRow.append(tableData);

                        tableData=document.createElement('td');
                        tableData.textContent=selectProduct.value;
                         tableRow.append(tableData);

                         tableData=document.createElement('td');
                         tableData.textContent=quantity.value;
                         tableRow.append(tableData);

                         tableData=document.createElement('td');
                         tableData.textContent=sellingPrice.value;
                         tableRow.append(tableData);

                         tableData=document.createElement('td');
                         tableData.textContent=totalPrice.value;
                         tableRow.append(tableData);

                         tableData=document.createElement('td');
                         tableData.innerHTML="<button class='"+numberRow+"' style='border:none'><i class='fa-solid fa-trash-can'></i></button>";
                         tableRow.append(tableData);

                          tableData=document.createElement('td');
                          tableData.textContent='VENTES';
                          tableData.className='d-none';
                          tableRow.append(tableData);

                         tableBody.append(tableRow);

                         let total=0;
                         let tvaValue=0;
                         const rows = document.querySelectorAll('#tableSaleTemp tbody tr');
                         rows.forEach(row => {
                           total=total+Number(row.childNodes[5].innerHTML);
                           if(tva.checked==true){
                           tvaValue=tvaValue+(Number(row.childNodes[5].innerHTML)*0.1925);
                           }
                           })

                         priceTotal.textContent=Math.round(total-tvaValue-remise.value);
                          totalTva.textContent=Math.round(Number(tvaValue));
                         totalPriceValue.value=Math.round(total-tvaValue-remise.value);

                        remiseValue.value=remise.value;
                        tvaValues.value=Math.round(tvaValue);
                        prixHT.textContent=total;
                          totalPrice.value=0;

              }else{alert('Moyen de paiement incorrect')}


                 }else{alert('Quantité non renseignée ou insuffisante');}
                 }else{alert('Lot non sélectionné');}
                 }else{alert('Produit non selectionné');
                 }
          }else if(services.value!='VENTES'){
          if(totalPrice.value!=0){
       if(paymentMethod.value=="A_CREDIT" && customer.value!="CLIENT INCONNU"){

            let table=document.querySelector('table');
            let numberRow=table.rows.length-1;
            let tableRow=document.createElement('tr');

            let tableData=document.createElement('td');
            tableData.textContent=numberRow;
            tableRow.append(tableData);

             tableData=document.createElement('td');
             tableData.textContent=services.value;
            tableRow.append(tableData);

            tableData=document.createElement('td');
            tableData.textContent=quantity.value;
             tableRow.append(tableData);

             tableData=document.createElement('td');
             tableData.textContent=1;
             tableRow.append(tableData);

             tableData=document.createElement('td');
             tableData.textContent=totalPrice.value;
             tableRow.append(tableData);

             tableData=document.createElement('td');
             tableData.textContent=totalPrice.value;
             tableRow.append(tableData);

             tableData=document.createElement('td');
             tableData.innerHTML="<button class='"+numberRow+"' style='border:none'><i class='fa-solid fa-trash-can'></i></button>";
             tableRow.append(tableData);

              tableData=document.createElement('td');
              tableData.textContent='SERVICES';
              tableData.className='d-none';
              tableRow.append(tableData);

             tableBody.append(tableRow);

             let total=0;
             let tvaValue=0;
             const rows = document.querySelectorAll('#tableSaleTemp tbody tr');
             rows.forEach(row => {
               total=total+Number(row.childNodes[5].innerHTML);
               if(tva.checked==true){
               tvaValue=tvaValue+(Number(row.childNodes[5].innerHTML)*0.1925);
               }
               })

             priceTotal.textContent=Math.round(total-tvaValue-remise.value);
              totalTva.textContent=Math.round(Number(tvaValue));
             totalPriceValue.value=Math.round(total-tvaValue-remise.value);

            remiseValue.value=remise.value;
            tvaValues.value=Math.round(tvaValue);
            prixHT.textContent=total;
             totalPrice.value=0;

      }else if(paymentMethod.value=="ESPECES"){

            let table=document.querySelector('table');
            let numberRow=table.rows.length-1;
            let tableRow=document.createElement('tr');
            let tableData=document.createElement('td');
            tableData.textContent=numberRow;
            tableRow.append(tableData);

            tableData=document.createElement('td');
            tableData.textContent=services.value;
            tableRow.append(tableData);

            tableData=document.createElement('td');
            tableData.textContent=quantity.value;
             tableRow.append(tableData);

             tableData=document.createElement('td');
             tableData.textContent=1;
             tableRow.append(tableData);

             tableData=document.createElement('td');
             tableData.textContent=totalPrice.value;
             tableRow.append(tableData);

             tableData=document.createElement('td');
             tableData.textContent=totalPrice.value;
             tableRow.append(tableData);

             tableData=document.createElement('td');
             tableData.innerHTML="<button class='"+numberRow+"' style='border:none'><i class='fa-solid fa-trash-can'></i></button>";
             tableRow.append(tableData);

             tableData=document.createElement('td');
             tableData.textContent='SERVICES';
             tableData.className='d-none';
             tableRow.append(tableData);

             tableBody.append(tableRow);

             let total=0;
             let tvaValue=0;
             const rows = document.querySelectorAll('#tableSaleTemp tbody tr');
             rows.forEach(row => {
               total=total+Number(row.childNodes[5].innerHTML);
               if(tva.checked==true){
               tvaValue=tvaValue+(Number(row.childNodes[5].innerHTML)*0.1925);
               }
               })

             priceTotal.textContent=Math.round(total-tvaValue-remise.value);
              totalTva.textContent=Math.round(Number(tvaValue));
             totalPriceValue.value=Math.round(total-tvaValue-remise.value);

            remiseValue.value=remise.value;
            tvaValues.value=Math.round(tvaValue);
            prixHT.textContent=total;
             totalPrice.value=0;

      }else{alert('Moyen de paiement incorrect')};

}else{alert("Bien vouloir renseigner le montant du service")}
         } else{alert('Produit non selectionné')};

          }else{
                   alert('Client non selectionné');
                   }
          }else{
               alert('Service non selectionné');
               }
          }  else{
               alert('Mode de paiement non selectionné');
               }}
          ajoutProduct();
      }else{
alert("Quantité insuffisante");
}

})

paymentMethod.addEventListener('change',()=>{paymentMethod.setAttribute('disabled',true)});

customer.addEventListener('change',()=>{customer.setAttribute('disabled',true)});


tableBody.addEventListener('click',()=>{
const rows = document.querySelectorAll('#tableSaleTemp tbody tr');
rows.forEach(row => {
if(row.childNodes[0].innerHTML!="Nº" && row.childNodes[0].innerHTML!="Prix HT"){
row.addEventListener('click', function() {
    let total=Number(priceTotal.innerHTML);
    if(total!=0){
    total=total-Number(row.childNodes[5].innerHTML);
         priceTotal.textContent=total;
          totalPriceValue.value=total;
             row.remove();

     prixHT.textContent=Math.round(total);

    }


});
}

});
});



const btnValidation=document.querySelector('#btn_validation');

btnValidation.addEventListener('click',() => {
const rows = document.querySelectorAll('#tableSaleTemp tbody tr');
const  tbodySale = document.querySelector('#tableSale tbody');
 tbodySale.innerHTML='';
   rows.forEach(row => {
let trTemp = document.createElement('tr');
    trTemp.innerHTML = `
 <td>
        <input type="text" name="products[${productIndex}]" value="${row.children[2].innerText}:${Number(row.children[3].innerText)}:${Number(row.children[5].innerText)}:${row.children[1].innerText}:${row.children[7].innerText}" placeholder="Nom du produit" class="form-control" />

       </td>`;
   tbodySale.append(trTemp);
   productIndex++;
  });
 })

 const getCustomer=document.querySelector('#getCustomer');
 const creationCustomer=document.querySelector('#btn_client');

 getCustomer.addEventListener('click',()=>{
 creationCustomer.click();
 })


 const selectCountry=document.querySelector('#countrySelect');
 const selectEnterprise=document.querySelector('#enterpriseSelect');
 const selectRef=document.querySelector('#selectRef');

 if(selectCountry!=null){

    async function getAllCountries(){
 selectCountry.innerHTML=''
 const urlCountry=`${baseURL}/api/v1/countries/get-all`;
 const response=await fetch(urlCountry, {
                                          method: 'GET',
                                          headers: {
                                              'Content-Type': 'application/json'
                                          }
                                      });
  if(!response.ok){
  }else{ const data=await response.json();
 let optionCountry=document.createElement('option');
 optionCountry.textContent=' Selectionner un pays';
 optionCountry.value=' Selectionner un pays';
 optionCountry.selected=true;
 optionCountry.disabled=true;
 selectCountry.append(optionCountry);

 data.forEach(country=>{
 optionCountry=document.createElement('option');
 optionCountry.textContent=country.name;
 optionCountry.value=country.name;
 selectCountry.append(optionCountry);
  })} }
   getAllCountries();
 }

 selectCountry.addEventListener('change',()=>{  async function getAllEnterprise(){
 selectEnterprise.innerHTML=''
 const urlEnterprise=`${baseURL}/api/v1/enterprises/get-all`;
 const response=await fetch(urlEnterprise, {
                                             method: 'GET',
                                             headers: {
                                                 'Content-Type': 'application/json'
                                             }
                                         });
 if(!response.ok){
 }else{
 const data=await response.json();

 let optionEnterprise=document.createElement('option');
 optionEnterprise.textContent=' Selectionner une entreprise';
 optionEnterprise.value=' Selectionner une entreprise';
 optionEnterprise.selected=true;
 optionEnterprise.disabled=true;
 selectEnterprise.append(optionEnterprise);

 data.forEach(enterprise=>{
 if(enterprise.country==selectCountry.value){
 let optionEnterprise=document.createElement('option');
 optionEnterprise.textContent=enterprise.name;
 optionEnterprise.value=enterprise.name;
 selectEnterprise.append(optionEnterprise);}})}}
  getAllEnterprise();
 })


 const typeSelect=document.querySelector('#typeSelect');
 const pointFocal=document.querySelector('#div_pointFocal');
 typeSelect.addEventListener('change',()=>{
 if(typeSelect.value=="FOURNISSEUR"){
 pointFocal.readonly=false;
 }else{
 pointFocal.readonly=true;
 }
 })



const updateCustomer=document.querySelector('#id_custom');

 document.querySelector('#table_customer tbody').addEventListener('click',()=>{
 const rows = document.querySelectorAll('#table_customer tbody tr');
 rows.forEach(row => {
     row.addEventListener('click', function() {
      const refCustomer=row.children[0].innerText;
      async function getCustomerByRef(){
      const urlCustom=`${baseURL}/api/v1/customers/get-by-ref/${refCustomer}`;
      const response=await fetch(urlCustom, {
                                              method: 'GET',
                                              headers: {
                                                  'Content-Type': 'application/json'
                                              }
                                          });
      if(!response.ok){}else{
      const data=await response.json();

      selectType.value=data.type;
      selectPays.value=data.country;
      selectEnt.value=data.enterprise;
      fullName.value=data.fullName;
      email.value=data.email;
      numberPhone.value=data.phoneNumber;
      adresses.value=data.adresse;
      selectRef.value=data.refCustomer;
      updateCustomer.click();
      }
      }
      getCustomerByRef();

     });
 });
 })










