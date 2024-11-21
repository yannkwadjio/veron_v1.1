const baseURL="/veron"
const viewBonCommande=document.querySelector('#viewBonCommande');
const viewStock=document.querySelector('#viewStock');
const containerPurchaseOrder=document.querySelector('#container_purchase_order');
const containerStock=document.querySelector('#container_stock');

viewBonCommande.addEventListener('click',()=>{
  containerPurchaseOrder.style.display='inline-block';
  containerPurchaseOrder.className='container w-75 d-flex flex-column';
  containerStock.style.display='none'
    containerStock.className='d-none';
  })

 viewStock.addEventListener('click',()=>{
 containerStock.style.display='inline-block';
 containerStock.className='container w-75 d-flex flex-column';
 containerPurchaseOrder.style.display='none'
  containerPurchaseOrder.className='d-none';
 })







const paymentMethod = document.querySelector('#paymentMethodOrder');
const selectEnterprise = document.querySelector('#enterpriseOrder');
const selectAgency = document.querySelector('#agencyOrder');
const selectProduct = document.querySelector('#productOrder');
const quantity = document.querySelector('#quantityOrder');
const stockFinal = document.querySelector('#stockFinalOrder');
const price = document.querySelector('#priceOrder');
const btnAjoutOrder = document.querySelector('#btn_ajout_Order');
const tbody = document.querySelector('#tableCommande tbody');
const table = document.querySelector('table');
const tva = document.querySelector('#tva');
const totalTva = document.querySelector('#totalTva');
const priceHT = document.querySelector('#priceHT');
const priceTotal = document.querySelector('#priceTotalOrder');
const remise = document.querySelector('#remiseOrder');
const sPaymentMethod=document.querySelector('#s_paymentMethod');
const sRemise=document.querySelector('#s_remise');
const sPriceHT=document.querySelector('#s_priceHT');
const priceTTC=document.querySelector('#priceTTC');
const sTva=document.querySelector('#s_tva');
const btnValidation=document.querySelector('#btn_validation');

let productIndex = 0;
quantity.value = 0;
remise.value = "0";
priceHT.value=0;
totalTva.value=0;
priceTotal.value=0;


paymentMethod.addEventListener('change', () => {
 paymentMethod.setAttribute('disabled', true);
 sPaymentMethod.value=paymentMethod.value;
 sPaymentMethod.textContent=paymentMethod.value;
});

selectEnterprise.addEventListener('change', () => {
    selectEnterprise.setAttribute('disabled', true);
    enterprise.value=selectEnterprise.value;
    enterprise.textContent=selectEnterprise.value;

    selectAgency.innerHTML='';

    let option = document.createElement('option');
            option.textContent = 'Sélectionner une agence';
            option.value = 'Sélectionner une agence';
            option.disabled = true;
            option.selected = true;
            selectAgency.append(option);


   option = document.createElement('option');
            option.textContent ="MAGASIN PRINCIPAL";
            option.value ="MAGASIN PRINCIPAL";

          selectAgency.append(option);

});


selectAgency.addEventListener('change', () => {
    selectAgency.setAttribute('disabled', true);
     agency.value=selectAgency.value;
     agency.textContent=selectAgency.value;
});

remise.addEventListener('change',()=>{
 remise.setAttribute('readonly', true);
     sRemise.value=remise.value;
     sRemise.textContent=remise.value;
})

async function getAllEnterprise() {
    const urlEnterprise = `${baseURL}/api/v1/enterprises/get-all`;
    const response = await fetch(urlEnterprise, {
                                                  method: 'GET',
                                                  headers: {
                                                      'Content-Type': 'application/json'
                                                  }
                                              });
    if (response.ok) {
        const data = await response.json();
        selectEnterprise.innerHTML = '';
        let option = document.createElement('option');
        option.textContent = 'Sélectionner une entreprise';
        option.value = 'Sélectionner une entreprise';
        option.disabled = true;
        option.selected = true;
        selectEnterprise.append(option);

        data.forEach(enterprise => {
            option = document.createElement('option');
            option.textContent = enterprise.name;
            option.value = enterprise.name;
            selectEnterprise.append(option);
        });
    }
}
getAllEnterprise();

async function getAllProducts() {
    const urlProduct = `${baseURL}/api/v1/products/get-all`;
    const response = await fetch(urlProduct, {
                                               method: 'GET',
                                               headers: {
                                                   'Content-Type': 'application/json'
                                               }
                                           });
    if (response.ok) {
        const data = await response.json();
        selectProduct.innerHTML = '';
        let option = document.createElement('option');
        option.textContent = 'Sélectionner un produit';
        option.value = 'Sélectionner un produit';
        option.disabled = true;
        option.selected = true;
        selectProduct.append(option);

        data.forEach(product => {
            option = document.createElement('option');
            option.textContent = product.name;
            option.value = product.name;
            selectProduct.append(option);


        });

 const element = document.querySelector('#productOrder');
  const choices = new Choices(element);
    }

}
getAllProducts();


selectProduct.addEventListener('change', () => {
    async function getProductByName() {
        const urlProductName = `${baseURL}/api/v1/products/get-by-name/${selectProduct.value}`;
        const response = await fetch(urlProductName, {
                                                       method: 'GET',
                                                       headers: {
                                                           'Content-Type': 'application/json'
                                                       }
                                                   });
        if (response.ok) {
            const data = await response.json();
            stockFinal.value = Number(data.finalStock);
            price.value = Number(data.purchasePrice);
        }
    }
    getProductByName();
});

btnAjoutOrder.addEventListener('click', () => {
 const rows = document.querySelectorAll('#tableCommande tbody tr');
let state=false;
    rows.forEach(row => {
    if(row.children[1].textContent==selectProduct.value){
  state=true;
    }

    });
    if(state==false){
     if (paymentMethod.value !== 'Sélectionner un mode de paiement') {
            if (selectEnterprise.value !== 'Sélectionner une entreprise') {
            if(selectAgency.value!=='Sélectionner une agence'){
            if (selectProduct.value !== 'Sélectionner un produit') {
                                if (Number(quantity.value) !== '' && Number(quantity.value) != 0) {
                                    let numberRow = table.rows.length - 1;
                                    async function getProductByName() {
                                        const urlProductName = `${baseURL}/api/v1/products/get-by-name/${selectProduct.value}`;
                                        const response = await fetch(urlProductName, {
                                                                                       method: 'GET',
                                                                                       headers: {
                                                                                           'Content-Type': 'application/json'
                                                                                       }
                                                                                   });
                                        if (response.ok) {
                                            const data = await response.json();

                                            let tr = document.createElement('tr');

                                            let tdRef = document.createElement('td');
                                            tdRef.textContent = data.refProduct;
                                            tdRef.value = data.refProduct;
                                            tr.append(tdRef);

                                            tdProduct = document.createElement('td');
                                            tdProduct.textContent = selectProduct.value;
                                            tdProduct.value = selectProduct.value;
                                            tr.append(tdProduct);

                                            tdQte = document.createElement('td');
                                            tdQte.textContent = quantity.value;
                                            tdQte.value = Number(quantity.value);
                                            tr.append(tdQte);

                                            tdPrice = document.createElement('td');
                                            tdPrice.textContent = price.value;
                                            tdPrice.value = Number(price.value);
                                            tr.append(tdPrice);

                                            tdTotalPrice = document.createElement('td');
                                            tdTotalPrice.textContent = Number(price.value) * Number(quantity.value);
                                            tdTotalPrice.value = Number(price.value) * Number(quantity.value);
                                            tr.append(tdTotalPrice);

                                            tdOpen = document.createElement('td');
                                            tdOpen.innerHTML = `<button class='${numberRow}' style='border:none'><i class='fa-solid fa-trash-can'></i></button>`;
                                            tr.append(tdOpen);

                                            tbody.append(tr);

                                            calculateTotal();

                                            quantity.value = 0;

                                           tva.setAttribute('disabled',true);

                                        } else {
                                            alert("Produit non trouvé");
                                        }
                                    }
                                    getProductByName();
                                } else {
                                    alert('Quantité non renseignée');
                                }
                            } else {
                                alert('Produit non sélectionné');
                            }
            }else {
                                        alert('Agence non sélectionné');
                                    }

            } else {
                alert('Entreprise non sélectionnée');
            }
        } else {
            alert('Mode de paiement non sélectionné');
        }
    }else{
    alert('Ce produit est déjà dans le panier');
    }

});

function calculateTotal() {
    let total = 0;
    let tvaValue = 0;
    const rows = document.querySelectorAll('#tableCommande tbody tr');
    rows.forEach(row => {
    if(row.children.length>4){
        let amount = Number(row.children[4].value);
            total += amount;
            if (tva.checked) {
                tvaValue += amount * 0.1925;
            }
    }

    });
    priceHT.innerText = Math.round(total);
    priceHT.value = Math.round(total);
    totalTva.textContent = Math.round(tvaValue);
    totalTva.value = Math.round(tvaValue);
    priceTotal.innerText = Math.round(total + tvaValue - Number(remise.value));
    priceTotal.value = Math.round(total + tvaValue - Number(remise.value));

    sPriceHT.value=priceHT.value;
    priceTTC.value=priceTotal.value;
    sTva.value=totalTva.value;

}
calculateTotal();


function updatePurchaseOrder(productName, totalPrice) {
    let tbodyPs = document.querySelector('#tablePurchaseOrder tbody');
    let trTemp = document.createElement('tr');
    trTemp.innerHTML = `
        <td>
            <input type="text" name="products[${productIndex}]" value="${productName}:${totalPrice}" placeholder="Nom du produit" class="form-control" />
        </td>`;
    tbodyPs.append(trTemp);
}


tbody.addEventListener('click', (event) => {
    if (event.target.closest('button')) {
        const row = event.target.closest('tr');
        row.remove();
        calculateTotal();
    }
});

btn_validation_Order.addEventListener('click',(event) => {

    const rows = document.querySelectorAll('#tableCommande tbody tr');
    if(rows.length>0){
let tbodyPs = document.querySelector('#tablePurchaseOrder tbody');
   tbodyPs.innerHTML='';
    rows.forEach(row => {
    tbodyPs = document.querySelector('#tablePurchaseOrder tbody');

        let trTemp = document.createElement('tr');
        trTemp.innerHTML = `
            <td>
                <input type="text" name="products[${productIndex}]" value="${row.children[1].value}:${row.children[2].value}:${row.children[3].value}" placeholder="Nom du produit" class="ss-main" />
            </td>`;
        tbodyPs.append(trTemp);
        productIndex++;
    });

    }else{
    event.preventDefault();
    alert("Aucune opération en attente de validation")
    }


})

const bc=document.querySelector('#bc');

async function getAllPurchaseOrder(){
const url=`${baseURL}/api/v1/purchases-order/get-all`;
const response=await fetch(url, {
                                  method: 'GET',
                                  headers: {
                                      'Content-Type': 'application/json'
                                  }
                              });
if(!response.ok){}else{
const data=await response.json();
bc.innerHTML='';
let option=document.createElement('option');
option.textContent='Sélectionner un bon de commande';
option.value='Sélectionner un bon de commande';
option.selected=true;
option.disabled=true;
bc.append(option);

data.forEach(order=>{
if(order.statutPurchase=="EN_INSTANCE"){
option=document.createElement('option');
option.textContent=order.refPurchaseOrder;
option.value=order.refPurchaseOrder;
bc.append(option);
}
})
  const choices = new Choices(bc);
}
}
getAllPurchaseOrder();

const date=document.querySelector('#date');
const status=document.querySelector('#status');
const agencies=document.querySelector('#agencies');
const method=document.querySelector('#method');
const priceH=document.querySelector('#priceH');
const refBC=document.querySelector('#refBC');

bc.addEventListener('change',()=>{
refBC.value=bc.value;
async function getByRefBc(){
const url=`${baseURL}/api/v1/purchases-order/get-by-ref/${bc.value}`;
const response=await fetch(url, {
                                  method: 'GET',
                                  headers: {
                                      'Content-Type': 'application/json'
                                  }
                              });
if(!response.ok){
}else{
const data=await response.json();

date.value=data.dateCreation;
status.value=data.statutPurchase;
agencies.value=data.agency;
method.value=data.paymentMethod;
priceH.value=data.priceHT;

 var y=0;
async function getCountLot(){
const urlLot=`${baseURL}/api/v1/counts-lot/get-all`;
const response=await fetch(urlLot, {
                                     method: 'GET',
                                     headers: {
                                         'Content-Type': 'application/json'
                                     }
                                 });
 if(!response.ok){
 }else{
 const data=await response.json();
 if(data.length>0){
y=Number(data.length+1)
 }else{
 y=0;
 }
 }
 return y;
}
y=await getCountLot();

const tbodyStock=document.querySelector('#table_stock tbody');
tbodyStock.innerHTML='';

for(let i=0;i<data.products.length;i++){
let item=data.products[i].split(':');

let refProduct1;
 async function getProductByName() {
        const urlProductName = `${baseURL}/api/v1/products/get-by-name/${item[0]}`;
        const response = await fetch(urlProductName, {
                                                       method: 'GET',
                                                       headers: {
                                                           'Content-Type': 'application/json'
                                                       }
                                                   });
        if (response.ok) {
            const data = await response.json();
            refProduct1 = data.refProduct;

   let tr=document.createElement("tr");
               tr.innerHTML=`<td>${refProduct1}</td>
                             <td>${item[0]}</td>
                             <td><input type='text' class='form-control w-50' value=${item[1]}></td>
                             <td>${item[2]}</td>
                             <td><input type="date" id="date${y+1}" name="date" class="form-control"></td>
                             <td><input type="text" class="form-control" name="lot" value="LOT0${y+1}"></td>
                             <td><div class="form-check form-switch"><input type="checkbox" name="validate" class="form-check-input"></div></td>
                            `
y++;
            tbodyStock.append(tr);
            }
    }
    getProductByName();

}

}
}
getByRefBc();
})


const tbodyOrder = document.querySelector('#tableOrder tbody');
btn_save.addEventListener('click', () => {
    const rows = document.querySelectorAll('#table_stock tbody tr');
    tbodyOrder.innerHTML = '';
    let products = [];

    rows.forEach(row => {
        let trTemp = document.createElement('tr');
        let status=row.children[6].firstChild.firstChild.defaultValue;
       if(status==""){
       status="off";
         trTemp.innerHTML = `
                   <td>
                       <input type="text" name="products[${productIndex}]" value="${row.children[0].innerText}:${row.children[1].innerText}:${Number(row.children[2].firstChild.defaultValue)}:${Number(row.children[3].innerText)}:${row.children[4].firstChild.defaultValue}:${row.children[5].firstChild.defaultValue}:${status}" placeholder="Nom du produit" class="form-control" />
                   </td>`;
       }else{
       status="on";
       trTemp.innerHTML = `
                          <td>
                              <input type="text" name="products[${productIndex}]" value="${row.children[0].innerText}:${row.children[1].innerText}:${Number(row.children[2].firstChild.defaultValue)}:${Number(row.children[3].innerText)}:${row.children[4].firstChild.defaultValue}:${row.children[5].firstChild.defaultValue}:${status}" placeholder="Nom du produit" class="form-control" />
                          </td>`;
       }

        products.push({
            name: row.children[0].innerText,
            desc: row.children[1].innerText,
            qty: Number(row.children[2].innerText),
            price: Number(row.children[3].innerText),
            category: row.children[4].innerText,
            code: row.children[5].innerText
        });

        tbodyOrder.append(trTemp);
        productIndex++;
    });

});



document.querySelector('#table_stock').addEventListener('change', (event) => {

    if (event.target.tagName.toLowerCase() === 'input') {
        let data = event.target.value;
        event.target.setAttribute('value',data);
    }
});


const agencyAppro=document.querySelector('#agencyAppro');
const store01=document.querySelector('#store01');
const store02=document.querySelector('#store02');
const productAppro=document.querySelector('#productAppro');
const stockAppro=document.querySelector('#stockAppro');
const table_appro=document.querySelector('#table_appro');
const lot=document.querySelector('#lotAppro');
const quantityAppro=document.querySelector('#quantityAppro');
const btnAjoutAppro=document.querySelector('#btn_ajout_appro');
const tbodyAppro=document.querySelector('#table_appro tbody');
const store001=document.querySelector('#store001');
const store002=document.querySelector('#store002');
const agency001=document.querySelector('#agency001');
const tbodyApproValid=document.querySelector('#tableAppro tbody');
const btnValidAppro=document.querySelector('#btn_valid_appro');


store01.addEventListener('change',()=>{store01.disabled=true})
store02.addEventListener('change',()=>{store02.disabled=true})
agencyAppro.addEventListener('change',()=>{agencyAppro.disabled=true})

agencyAppro.addEventListener('change',()=>{
agency001.value=agencyAppro.value;
async function getAllStores(){
const url=`${baseURL}/api/v1/stores/get-all`;
const response=await fetch(url, {
                                  method: 'GET',
                                  headers: {
                                      'Content-Type': 'application/json'
                                  }
                              });
if(!response.ok){
}else{
const data=await response.json();
store01.innerHTML='';
let option=document.createElement('option');
option.textContent='Sélectionner un magasin';
option.value='Sélectionner un magasin';
option.selected=true;
option.disabled=true;
store01.append(option);

option=document.createElement('option');
option.textContent='STORE01';
option.value='STORE01';
store01.append(option);


data.forEach(store=>{
if(store.agencies==agencyAppro.value){
option=document.createElement('option');
option.textContent=store.refStore;
option.value=store.refStore;
store01.append(option);
}
})

}
}
getAllStores();
})

store01.addEventListener('change',()=>{
store001.value=store01.value;
async function getAllStores(){
const url=`${baseURL}/api/v1/stores/get-all`;
const response=await fetch(url, {
                                  method: 'GET',
                                  headers: {
                                      'Content-Type': 'application/json'
                                  }
                              });
if(!response.ok){
}else{
const data=await response.json();
store02.innerHTML='';
let option=document.createElement('option');
option.textContent='Sélectionner un magasin';
option.value='Sélectionner un magasin';
option.selected=true;
option.disabled=true;
store02.append(option);

if(store01.value!="STORE01"){
option=document.createElement('option');
option.textContent="STORE01";
option.value="STORE01";
store02.append(option);
}

data.forEach(store=>{
if(store.refStore !=store01.value && store.agencies==agencyAppro.value){
option=document.createElement('option');
option.textContent=store.refStore;
option.value=store.refStore;
store02.append(option);
}

})

}
}
getAllStores();
})


store01.addEventListener('change',()=>{
async function getAllProductStock(){
const url=`${baseURL}/api/v1/products-stock/get-all`;
const response=await fetch(url, {
                                  method: 'GET',
                                  headers: {
                                      'Content-Type': 'application/json'
                                  }
                              });
if(!response.ok){
}else{
const data=await response.json();
productAppro.innerHTML='';
let option=document.createElement('option');
option.textContent='Sélectionner un article';
option.value='Sélectionner un article';
option.selected=true;
option.disabled=true;
productAppro.append(option);
let listProducts=new Set();
data.forEach(product=>{
if(product.store == store01.value && !listProducts.has(product.name)){
option=document.createElement('option');
option.textContent=product.name;
option.value=product.name;
productAppro.append(option);
listProducts.add(product.name);
}

})
  const choices = new Choices(productAppro);
}
}
getAllProductStock();
})

store02.addEventListener('change',()=>{
store002.value=store02.value;
})

let choicesLot;
productAppro.addEventListener('change',()=>{
   if (choicesLot) {
        choicesLot.destroy();
    }
    stockAppro.value=0;
    quantityAppro.value=0;
async function getAllProductStock(){
const url=`${baseURL}/api/v1/products-stock/get-all`;
const response=await fetch(url, {
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
option.value='Sélectionner un  lot';
option.selected=true;
option.disabled=true;
lot.append(option);

let listProducts=new Set();
data.forEach(product=>{
if(product.name == productAppro.value && !listProducts.has(product.lot)){
option=document.createElement('option');
option.textContent=product.lot;
option.value=product.lot;
lot.append(option);
listProducts.add(product.lot);
}

})
 choicesLot = new Choices(lot);
}
}
getAllProductStock();
})


lot.addEventListener('change',()=>{
async function getAllProductStock(){
const url=`${baseURL}/api/v1/products-stock/get-all`;
const response=await fetch(url, {
                                  method: 'GET',
                                  headers: {
                                      'Content-Type': 'application/json'
                                  }
                              });
if(!response.ok){
}else{
const data=await response.json();
let valueStock=0;

data.forEach(product=>{
if(product.lot == lot.value && product.store==store01.value){
valueStock+=product.finalStock;
}

})
stockAppro.value=valueStock;
}
}
getAllProductStock();
})

btnAjoutAppro.addEventListener('click',()=>{
let totalQuantity=0;
const rows=document.querySelectorAll('#table_appro tbody tr');
let state=false;
rows.forEach(row=>{
if(row.children[1].innerText==productAppro.value && row.children[3].innerText==lot.value){
totalQuantity+=Number(row.children[2].innerText);
state=true;
}
})

if(state==false){
if(Number(quantityAppro.value)+totalQuantity<=Number(stockAppro.value) && lot.value!="Sélectionner un lot"){
 async function getProductByName() {
        const urlProductName = `${baseURL}/api/v1/products/get-by-name/${productAppro.value}`;
        const response = await fetch(urlProductName, {
                                                       method: 'GET',
                                                       headers: {
                                                           'Content-Type': 'application/json'
                                                       }
                                                   });
        if (response.ok) {
            const data = await response.json();
            let tr=document.createElement('tr');
            if(store01.value!="STORE01"){
                tr.innerHTML=`<td>${data.refProduct}</td>
                        <td>${productAppro.value}</td>
                        <td>${quantityAppro.value}</td>
                         <td>${lot.value}</td>
                         <td>${agencyAppro.value}</td>
                         <td><button style='border:none'><i class='fa-solid fa-trash-can'></i></button></td>
                        `
            }else{
             tr.innerHTML=`<td>${data.refProduct}</td>
                                    <td>${productAppro.value}</td>
                                    <td>${quantityAppro.value}</td>
                                     <td>${lot.value}</td>
                                     <td>MAGASIN PRINCIPAL</td>
                                     <td><button style='border:none'><i class='fa-solid fa-trash-can'></i></button></td>
                                    `
            }

            tbodyAppro.append(tr);
        }
    }
    getProductByName();
}else{
alert('Stock insuffisant');
}
}else{alert('Ce lot existe déjà dans le panier')}

})


document.querySelector('#table_appro tbody').addEventListener('click', (event) => {
    if (event.target.closest('button')) {
        const row = event.target.closest('tr');
        row.remove();
    }
});

productIndex=0;
btnValidAppro.addEventListener('click',()=>{
const rows = document.querySelectorAll('#table_appro tbody tr');
    tbodyApproValid.innerHTML = '';
    let products = [];

    rows.forEach(row => {
         let trTemp = document.createElement('tr');
               trTemp.innerHTML = `
                   <td>
                       <input type="text" name="products[${productIndex}]" value="${row.children[0].textContent}:${row.children[1].textContent}:${Number(row.children[2].textContent)}:${row.children[3].textContent}:${row.children[4].textContent}" placeholder="Nom du produit" class="form-control" />
                   </td>`;

               tbodyApproValid.append(trTemp);
               productIndex++;
    });


})


const agencySorti=document.querySelector('#agencySorti');
const storeSorti=document.querySelector('#storeSorti');
const productSorti=document.querySelector('#productSorti');
const stockSorti=document.querySelector('#stockSorti');
const agency0001=document.querySelector('#agency0001');
const store0001=document.querySelector('#store0001');
const btnAjoutSorti=document.querySelector('#btn_ajout_sorti');
const quantitySorti=document.querySelector('#quantitySorti');
const btnSorti=document.querySelector('#btn_sorti');
const tBodySorti=document.querySelector('#tableSorti tbody')

agencySorti.addEventListener('change',()=>{
agencySorti.disabled=true;
agency0001.value=agencySorti.value;
async function getAllStores(){
const url=`${baseURL}/api/v1/stores/get-all`;
const response=await fetch(url, {
                                  method: 'GET',
                                  headers: {
                                      'Content-Type': 'application/json'
                                  }
                              });
if(!response.ok){
}else{
const data=await response.json();
storeSorti.innerHTML='';
let option=document.createElement('option');
option.textContent='Sélectionner un magasin';
option.value='Sélectionner un magasin';
option.selected=true;
option.disabled=true;
storeSorti.append(option);

data.forEach(store=>{
if(store.agencies==agencySorti.value){
option=document.createElement('option');
option.textContent=store.refStore;
option.value=store.refStore;
storeSorti.append(option);
}

})
}
}
getAllStores();

})


let choiceProduct;
storeSorti.addEventListener('change',()=>{
   if (choiceProduct) {
        choiceProduct.destroy();
    }
store0001.value=storeSorti.value;
async function getAllProductStock1(){
const url=`${baseURL}/api/v1/products-stock/get-all`;
const response=await fetch(url, {
                                  method: 'GET',
                                  headers: {
                                      'Content-Type': 'application/json'
                                  }
                              });
if(!response.ok){
}else{
const data=await response.json();
productSorti.innerHTML='';
let option=document.createElement('option');
option.textContent='Sélectionner une fourniture';
option.value='Sélectionner une fourniture';
option.selected=true;
option.disabled=true;
productSorti.append(option);
let listProducts=new Set();
data.forEach(product=>{
if(product.store == storeSorti.value && !listProducts.has(product.name) && product.category=='FOURNITURES'){
option=document.createElement('option');
option.textContent=product.name;
option.value=product.name;
productSorti.append(option);
listProducts.add(product.name);
}

})
choiceProduct = new Choices(productSorti);
}
}
getAllProductStock1();
})


productSorti.addEventListener('change',()=>{
async function getAllStores(){
const url=`${baseURL}/api/v1/products-stock/get-all`;
const response=await fetch(url, {
                                  method: 'GET',
                                  headers: {
                                      'Content-Type': 'application/json'
                                  }
                              });
if(!response.ok){
}else{
const data=await response.json();
stockSorti.innerHTML='';
let stockNumber=0;
data.forEach(store=>{
if(store.store==storeSorti.value && store.name==productSorti.value){
stockNumber+=store.finalStock;
stockSorti.value=stockNumber;
}

})
}
}
getAllStores();
})

btnAjoutSorti.addEventListener('click',()=>{
let totalQuantity=0;
const rows=document.querySelectorAll('#table_sorti tbody tr');
rows.forEach(row=>{
totalQuantity+=Number(row.children[3].innerText);
})


if(Number(quantitySorti.value)+totalQuantity<=Number(stockSorti.value)){
 async function getProductByName() {
        const urlProductName = `${baseURL}/api/v1/products/get-by-name/${productSorti.value}`;
        const response = await fetch(urlProductName, {
                                                       method: 'GET',
                                                       headers: {
                                                           'Content-Type': 'application/json'
                                                       }
                                                   });
        if (response.ok) {
            const data = await response.json();
            let tr=document.createElement('tr');
            tr.innerHTML=`<td>${data.refProduct}</td>
            <td>${store0001.value}</td>
            <td>${data.name}</td>
             <td>${quantitySorti.value}</td>
            `
            document.querySelector('#table_sorti tbody').append(tr);
        }
    }
    getProductByName();
}else{
alert('Stock insuffisant');
}

})

btnSorti.addEventListener('click',()=>{
        const rows = document.querySelectorAll('#table_sorti tbody tr');
            tBodySorti.innerHTML = '';
            let products = [];

            rows.forEach(row => {
                 let trTemp = document.createElement('tr');
                       trTemp.innerHTML = `
                           <td>
                               <input type="text" name="products[${productIndex}]" value="${row.children[0].textContent}:${row.children[1].textContent}:${row.children[2].textContent}:${row.children[3].textContent}" placeholder="Nom du produit" class="form-control" />
                           </td>`;

                       tBodySorti.append(trTemp);
                       productIndex++;
            });


        })






