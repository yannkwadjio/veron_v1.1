const baseURL="/veron"
const cashType=document.querySelector('#cashType');
const motif=document.querySelector('#motif');
const sens=document.querySelector('#sens');
const refCash=document.querySelector('#refCash');
const cashId=document.querySelector("#cashId");
const description=document.querySelector("#description");
const reference=document.querySelector('#reference');
const amount=document.querySelector('#amount');

let choiceMotif;
 let choicesCashType = new Choices(cashType);


cashId.value=refCash.innerText;


motif.addEventListener('change',()=>{
let dte=new Date()
description.value=cashType.value+" "+motif.value+" du "+dte.getDay()+"/"+dte.getMonth()+"/"+dte.getYear()
reference.value=motif.value
if(cashType.value=="BON_DE_COMANDE"){
async function getPurchaseOrderByRef(){
const url=`${baseURL}/api/v1/purchases-order/get-by-ref/${motif.value}`
const response=await fetch(url, {
                                  method: 'GET',
                                  headers: {
                                      'Content-Type': 'application/json'
                                  }
                              });
if(!response.ok){
}else{
const data=await response.json();
reference.value=data.refPurchaseOrder
reference.readOnly=true;
amount.value=data.priceHT;
amount.readOnly=true;
}
}
getPurchaseOrderByRef();
}

})

cashType.addEventListener('change',()=>{
  if (choiceMotif) {
        choiceMotif.destroy();
    }
if(cashType.value=="APPRO_CAISSE_EN_ENTREE"){
reference.value=''
reference.readOnly=false;
amount.readOnly=false;
amount.value=0
motif.innerHTML='';
let option=document.createElement('option');
option.textContent=' Selectionner une opération';
option.selected=true;
option.disabled=true;
motif.append(option);

 async function getAllMvtCash(){
 const urlMvtCash=`${baseURL}/api/v1/mvt-cash/get-all`;
 const response=await fetch(urlMvtCash, {
 method: 'GET',
headers: {
  'Content-Type': 'application/json'
   }
 });
 if(!response.ok){}else{
 const data=await response.json();
data.forEach(mvtCash=>{
if(mvtCash.validated==false && mvtCash.motif==cashId.value){
option=document.createElement('option');
  option.textContent=mvtCash.refOperationCash;
  motif.append(option);
}
})
  choiceMotif=new Choices(motif)
 }
 }
 getAllMvtCash();


sens.innerHTML='';
option=document.createElement('option');
option.textContent=' Sélectionner le sens';
option.selected=true;
option.disabled=true;
sens.append(option);

option=document.createElement('option');
option.textContent='ENCAISSSEMENT';
sens.append(option);


}else if(cashType.value=="APPRO_CAISSE_EN_SORTIE"){
reference.value=''
reference.readOnly=false;
amount.readOnly=false;
amount.value=0
 motif.innerHTML='';
 let option=document.createElement('option');
 option.textContent=' Selectionner une caisse';
 option.selected=true;
 option.disabled=true;
 motif.append(option);

 var agency;
  async function getByRef(){
  const urlAgency=`${baseURL}/api/v1/cashes/get-by-cash/${cashId.value}`;
  const response=await fetch(urlAgency, {
                                          method: 'GET',
                                          headers: {
                                              'Content-Type': 'application/json'
                                          }
                                      });
  if(!response.ok){}else{
  const data=await response.json();
  agency=data.agency;
  }
  }
  getByRef();

 async function getAllCashes(){
 const urlCash=`${baseURL}/api/v1/cashes/get-all`;
 const response=await fetch(urlCash, {
                                       method: 'GET',
                                       headers: {
                                           'Content-Type': 'application/json'
                                       }
                                   });
 if(!response.ok){
 }else{
 const data=await response.json();
 data.forEach(cash=>{
 if(cash.refCash!=cashId.value && cash.agency==agency){
  option=document.createElement('option');
  option.textContent=cash.refCash;
  motif.append(option);
 }
 })
   choiceMotif=new Choices(motif)
 }

 }
 getAllCashes();

sens.innerHTML='';
option=document.createElement('option');
option.textContent=' Sélectionner le sens';
option.selected=true;
option.disabled=true;
sens.append(option);


 option=document.createElement('option');
 option.textContent='DECAISSEMENT';
 sens.append(option);


 }else if(cashType.value=="RETRAIT_POUR_VERSEMENT_BANQUE"){
 reference.value=''
 reference.readOnly=false;
 amount.readOnly=false;
 amount.value=0
 motif.innerHTML='';
 let option=document.createElement('option');
 option.textContent=' Selectionner un compte bancaire';
 option.selected=true;
 option.disabled=true;
 motif.append(option);

sens.innerHTML='';
option=document.createElement('option');
option.textContent=' Sélectionner le sens';
option.selected=true;
option.disabled=true;
sens.append(option);

option=document.createElement('option');
option.textContent='DECAISSEMENT';
sens.append(option);

 async function getAllBanks(){
 const urlBank=`${baseURL}/api/v1/bank-account/get-all`;
 const response=await fetch(urlBank, {
                                       method: 'GET',
                                       headers: {
                                           'Content-Type': 'application/json'
                                       }
                                   });
 if(!response.ok){
 }else{
 const data=await response.json();
 data.forEach(bank=>{
 option=document.createElement('option');
 option.textContent=bank.bankAccountNumber;
  option.value=bank.bankAccountNumber;
 motif.append(option);
 })
   choiceMotif=new Choices(motif)
 }

 }
 getAllBanks();

 }else if(cashType.value=="AVANCE_PERCUE"){
   reference.value=''
   reference.readOnly=false;
   amount.readOnly=false;
   amount.value=0
     motif.innerHTML='';
     let option=document.createElement('option');
     option.textContent=' Selectionner un client';
     option.selected=true;
     option.disabled=true;
     motif.append(option);

     sens.innerHTML='';
     option=document.createElement('option');
     option.textContent=' Sélectionner le sens';
     option.selected=true;
     option.disabled=true;
     sens.append(option);

  option=document.createElement('option');
  option.textContent='ENCAISSEMENT';
  sens.append(option);

     async function getAllCustomer(){
     const urlCustomer=`${baseURL}/api/v1/customers/get-all`;
     const response=await fetch(urlCustomer, {
                                             method: 'GET',
                                             headers: {
                                                 'Content-Type': 'application/json'
                                             }
                                         });
     if(!response.ok){
     }else{
     const data=await response.json();
     data.forEach(customer=>{
     if(customer.fullName!="CLIENT INCONNU"){
       option=document.createElement('option');
          option.textContent=customer.fullName;
          option.value=customer.fullName;
          motif.append(option);
     }

     })
       choiceMotif=new Choices(motif)
     }

     }
     getAllCustomer();

     }else if(cashType.value=="DEPENSES"){
 reference.value=''
 reference.readOnly=false;
 amount.readOnly=false;
 amount.value=0
   motif.innerHTML='';
   let option=document.createElement('option');
   option.textContent=' Selectionner une depense';
   option.selected=true;
   option.disabled=true;
   motif.append(option);

   sens.innerHTML='';
   option=document.createElement('option');
   option.textContent=' Sélectionner le sens';
   option.selected=true;
   option.disabled=true;
   sens.append(option);

option=document.createElement('option');
option.textContent='DECAISSEMENT';
sens.append(option);

   async function getAllSpents(){
   const urlSpents=`${baseURL}/api/v1/spents/get-all`;
   const response=await fetch(urlSpents, {
                                           method: 'GET',
                                           headers: {
                                               'Content-Type': 'application/json'
                                           }
                                       });
   if(!response.ok){
   }else{
   const data=await response.json();
   data.forEach(spents=>{
   option=document.createElement('option');
   option.textContent=spents.name;
   motif.append(option);
   })
     choiceMotif=new Choices(motif)
   }

   }
   getAllSpents();
   }else if(cashType.value=="AUTRES_VERSEMENTS"){
   reference.value=''
   reference.readOnly=false;
   amount.readOnly=false;
   amount.value=0
    motif.innerHTML='';
    let option=document.createElement('option');
    option.textContent=' Selectionner un motif';
    option.selected=true;
    option.disabled=true;
    motif.append(option);

    option=document.createElement('option');
    option.textContent='AUTRES_VERSEMENTS';
    motif.append(option);
      choiceMotif=new Choices(motif)

sens.innerHTML='';
option=document.createElement('option');
option.textContent=' Sélectionner le sens';
option.selected=true;
option.disabled=true;
sens.append(option);

    option=document.createElement('option');
    option.textContent='ENCAISSEMENT';
    sens.append(option);


    }else if(cashType.value=="AUTRES_RETRAITS"){
    reference.value=''
    reference.readOnly=false;
    amount.readOnly=false;
    amount.value=0
         motif.innerHTML='';
         let option=document.createElement('option');
         option.textContent=' Selectionner un motif';
         option.selected=true;
         option.disabled=true;
         motif.append(option);

         option=document.createElement('option');
         option.textContent='AUTRES_RETRAITS';
         motif.append(option);
           choiceMotif=new Choices(motif)

sens.innerHTML='';
option=document.createElement('option');
option.textContent=' Sélectionner le sens';
option.selected=true;
option.disabled=true;
sens.append(option);

         sens.innerHTML='';
         option=document.createElement('option');
         option.textContent='DECAISSEMENT';
         sens.append(option);


         }else if(cashType.value=="REGULARISATIONS"){
         reference.value=''
         reference.readOnly=false;
         amount.readOnly=false;
         amount.value=0
                  motif.innerHTML='';
                  let option=document.createElement('option');
                  option.textContent=' Selectionner un motif';
                  option.selected=true;
                  option.disabled=true;
                  motif.append(option);

                  option=document.createElement('option');
                  option.textContent='REGULARISATIONS';
                  motif.append(option);
                    choiceMotif=new Choices(motif)

                  sens.innerHTML='';

                  option=document.createElement('option');
                                    option.textContent=' Selectionner un sens';
                                    option.selected=true;
                                    option.disabled=true;
                                    sens.append(option);

                  option=document.createElement('option');
                  option.textContent='ENCAISSEMENT';
                  sens.append(option);

                  option=document.createElement('option');
                  option.textContent='DECAISSEMENT';
                  sens.append(option);


                  }else if(cashType.value=="VERSEMENT_MANQUANTS"){
                  reference.value=''
                  reference.readOnly=false;
                  amount.readOnly=false;
                  amount.value=0
                                     motif.innerHTML='';
                                     let option=document.createElement('option');
                                     option.textContent=' Selectionner un motif';
                                     option.selected=true;
                                     option.disabled=true;
                                     motif.append(option);



                   async function getAllMissingCash(){
                    const urlMissing=`${baseURL}/api/v1/missing-cash/get-all`;
                    const response=await fetch(urlMissing, {
                                                             method: 'GET',
                                                             headers: {
                                                                 'Content-Type': 'application/json'
                                                             }
                                                         });
                    if(!response.ok){
                    }else{
                    const data=await response.json();
                    data.forEach(missing=>{
                    option=document.createElement('option');
                      option.textContent=missing.refMissingCash;
                      motif.append(option);
                    })
  choiceMotif=new Choices(motif)
                    }
                    }

getAllMissingCash();

sens.innerHTML='';
option=document.createElement('option');
option.textContent=' Sélectionner le sens';
sens.append(option);

 option=document.createElement('option');
 option.textContent='ENCAISSEMENT';
 option.selected=true;
 option.disabled=true;
 sens.append(option);

  }else if(cashType.value=="APPRO_CAISSE_VIA_LA_BANQUE"){
  reference.value=''
  reference.readOnly=false;
  amount.readOnly=false;
  amount.value=0
        motif.innerHTML='';
  let option=document.createElement('option');
  option.textContent=' Selectionner un compte bancaire';
  option.selected=true;
  option.disabled=true;
   motif.append(option);

async function getAllBanks(){
const urlBank=`${baseURL}/api/v1/bank-account/get-all`;
 const response=await fetch(urlBank, {
                                       method: 'GET',
                                       headers: {
                                           'Content-Type': 'application/json'
                                       }
                                   });
 if(!response.ok){
 }else{
const data=await response.json();
data.forEach(bank=>{
 option=document.createElement('option');
option.textContent=bank.bankAccountNumber;
 motif.append(option);
 })
   choiceMotif=new Choices(motif)
 }
}
getAllBanks();

sens.innerHTML='';
option=document.createElement('option');
option.textContent=' Sélectionner le sens';
option.selected=true;
option.disabled=true;
sens.append(option);


 option=document.createElement('option');
option.textContent='ENCAISSEMENT';
sens.append(option);


 }else if(cashType.value=="BON_DE_COMANDE"){
 reference.value=''
 reference.readOnly=false;
 amount.readOnly=false;
 amount.value=0
          motif.innerHTML='';
    let option=document.createElement('option');
    option.textContent=' Selectionner un bon de commande';
    option.selected=true;
    option.disabled=true;
     motif.append(option);

  async function getAllPurchaseOrders(){
  const urlBank=`${baseURL}/api/v1/purchases-order/get-all`;
   const response=await fetch(urlBank, {
                                         method: 'GET',
                                         headers: {
                                             'Content-Type': 'application/json'
                                         }
                                     });
   if(!response.ok){
   }else{
  const data=await response.json();
  data.forEach(purchaseOrder=>{
  if(purchaseOrder.statutPurchase=='CONFIRME' && purchaseOrder.paymentMethod=="ESPECES"){
     option=document.createElement('option');
    option.textContent=purchaseOrder.refPurchaseOrder;
     motif.append(option);
  }
   })
     choiceMotif=new Choices(motif)
   }
  }
  getAllPurchaseOrders();

  sens.innerHTML='';
  option=document.createElement('option');
  option.textContent=' Sélectionner le sens';
  option.selected=true;
  option.disabled=true;
  sens.append(option);


   option=document.createElement('option');
  option.textContent='DECAISSEMENT';
  sens.append(option);
   }
})


motif.addEventListener('change',()=>{
if(cashType.value=='VERSEMENT_MANQUANTS' && motif.value != ' Selectionner un motif'){
 sens.innerHTML='';
option=document.createElement('option');
option.textContent=' Selectionner un sens';
option.selected=true;
option.disabled=true;
sens.append(option);

option=document.createElement('option');
option.textContent='ENCAISSEMENT';
sens.append(option);


async function getMissingByRef(){
const url=`${baseURL}/api/v1/missing-cash/get-by-ref/${motif.value}`
const response=await fetch(url, {
                                  method: 'GET',
                                  headers: {
                                      'Content-Type': 'application/json'
                                  }
                              });
if(response.ok){
const data=await response.json();
description.value='Reste à verser: '+data.rest;
}
}
getMissingByRef();
}else if(cashType.value=='APPRO_CAISSE_EN_ENTREE' && motif.value != ' Selectionner une opération'){
async function getMvtCash(){
const url=`${baseURL}/api/v1/mvt-cash/get-mvt-by-ref/${motif.value}`
const response=await fetch(url, {
   method: 'GET',
   headers: {
   'Content-Type': 'application/json'
    }
  });
if(response.ok){
const data=await response.json();
amount.value=data.amount;
amount.readOnly=true;
}

}
getMvtCash()

}

})

