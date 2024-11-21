const baseURL="/veron"
const viewEnterprise=document.querySelector('#viewEnterprise');
const containerEnterprise=document.querySelector('#container_enterprise');
const containerAgency=document.querySelector('#container_agency');
const containerUser=document.querySelector('#container_user');
const viewAgency=document.querySelector('#viewAgency');
const viewUser=document.querySelector('#viewUser');
const roleUsers=document.querySelector("#roleUsers");
const viewPassword=document.querySelector('#viewPassword');
const containerPassword=document.querySelector('#container_password');
const containerDashboard=document.querySelector('#container_dashboard');
const btn_raz=document.querySelector('#btn-raz');
let code=document.querySelector('#code');



containerDashboard.display='none';
containerDashboard.className="d-none"

if(roleUsers.textContent=='ADMIN'){
containerDashboard.display='inline-block';
containerDashboard.className="container d-flex flex-wrap"
viewEnterprise.addEventListener('click',()=>{
containerEnterprise.style.display='inline-block';
containerEnterprise.className='container w-75 d-flex flex-column';
containerAgency.className='container w-75 d-none';
containerAgency.style.display='none';
containerUser.style.display='none';
containerUser.className='container w-75 d-none';
containerPassword.style.display='none';
containerPassword.className='container w-75 d-none';
containerDashboard.display='none';
containerDashboard.className="d-none";
})


viewAgency.addEventListener('click',()=>{
containerEnterprise.style.display='none';
containerEnterprise.className='container w-75 d-none';
containerAgency.style.display='inline-block';
containerAgency.className='container w-75 d-flex flex-column';
containerUser.style.display='none';
containerUser.className='container w-75 d-none';
containerPassword.style.display='none';
containerPassword.className='container w-75 d-none';
containerDashboard.display='none';
containerDashboard.className="d-none";
})

viewUser.addEventListener('click',()=>{
containerEnterprise.style.display='none';
containerEnterprise.className='container w-75 d-none';
containerAgency.style.display='none';
containerAgency.className='container w-75 d-none';
containerPassword.style.display='none';
containerPassword.className='container w-75 d-none';
containerUser.style.display='inline-block';
containerUser.className='container w-75 d-flex flex-column';
containerDashboard.display='none';
containerDashboard.className="d-none";
})

viewPassword.addEventListener('click',()=>{
containerEnterprise.style.display='none';
containerEnterprise.className='container w-75 d-none';
containerAgency.style.display='none';
containerAgency.className='container w-75 d-none';
containerPassword.style.display='none';
containerUser.style.display='none';
containerUser.className='container w-75 d-none';
containerPassword.style.display='inline-block';
containerPassword.className='container w-75 d-flex flex-column';
containerDashboard.display='none';
containerDashboard.className="d-none";
})




const entiteSource = document.querySelector("#entiteSource");
const entiteDestination = document.querySelector("#entiteDestination");
const source = document.querySelector('#source');
const destination = document.querySelector('#destination');

async function getAllEntitySource(){

entiteSource.innerHTML=''

const urlEntiteSource=`${baseURL}/api/v1/entities/get-all`

const response=await fetch(urlEntiteSource, {
                                              method: 'GET',
                                              headers: {
                                                  'Content-Type': 'application/json'
                                              }
                                          });

if(!response.ok){

}else{
const data=await response.json()
let optionEntiteSource=document.createElement('option');
optionEntiteSource.textContent=' Sélectionner une entité source';
optionEntiteSource.value=' Sélectionner une entité source';
optionEntiteSource.selected=true;
optionEntiteSource.disabled=true;
entiteSource.append(optionEntiteSource);

data.forEach(entite=>{
optionEntiteSource=document.createElement('option');
optionEntiteSource.textContent=entite;
optionEntiteSource.value=entite;
entiteSource.append(optionEntiteSource);
})


}

}

getAllEntitySource();


entiteSource.addEventListener('change',()=>{
if(entiteSource.value=="PAYS"){
    entiteDestination.innerHTML='';
    let optionEntiteDestination=document.createElement('option');
    optionEntiteDestination.textContent=' Sélectionner une entité destination';
    optionEntiteDestination.value=' Sélectionner une entité destination';
    optionEntiteDestination.selected=true;
    optionEntiteDestination.disabled=true;
    entiteDestination.append(optionEntiteDestination);


 optionEntiteDestination=document.createElement('option');
        optionEntiteDestination.textContent='PAYS';
        optionEntiteDestination.value='PAYS';
        entiteDestination.append(optionEntiteDestination);

    optionEntiteDestination=document.createElement('option');
        optionEntiteDestination.textContent='COMPAGNIE';
        optionEntiteDestination.value='COMPAGNIE';
        entiteDestination.append(optionEntiteDestination);



        async function getAllCountries(){
         const urlSource=`${baseURL}/api/v1/countries/get-all`;
         const response=await fetch(urlSource, {
                                                 method: 'GET',
                                                 headers: {
                                                     'Content-Type': 'application/json'
                                                 }
                                             });
         if(!response.ok){

         }else{
         const data=await response.json();
         source.innerHTML='';
         let optionCountry=document.createElement('option');
           optionCountry.textContent=' Sélectionner un pays';
             optionCountry.value=' Sélectionner un pays';
             optionCountry.selected=true;
             optionCountry.disabled=true;
             source.append(optionCountry);

             data.forEach(country=>{
             optionCountry=document.createElement('option');
             optionCountry.textContent=country.name;
             optionCountry.value=country.name;
              source.append(optionCountry);
             })
         }
        }
        getAllCountries();


}else if(entiteSource.value=="COMPAGNIE"){
 entiteDestination.innerHTML='';
    let optionEntiteDestination=document.createElement('option');
    optionEntiteDestination.textContent=' Sélectionner une entité destination';
    optionEntiteDestination.value=' Sélectionner une entité destination';
    optionEntiteDestination.selected=true;
    optionEntiteDestination.disabled=true;
    entiteDestination.append(optionEntiteDestination);

    optionEntiteDestination=document.createElement('option');
        optionEntiteDestination.textContent='PAYS';
        optionEntiteDestination.value='PAYS';
        entiteDestination.append(optionEntiteDestination);
         optionEntiteDestination=document.createElement('option');
                optionEntiteDestination.textContent='AGENCE';
                optionEntiteDestination.value='AGENCE';
                entiteDestination.append(optionEntiteDestination);

                  optionEntiteDestination=document.createElement('option');
               optionEntiteDestination.textContent='BANQUE';
               optionEntiteDestination.value='BANQUE';
                 entiteDestination.append(optionEntiteDestination);

                   optionEntiteDestination=document.createElement('option');
                                 optionEntiteDestination.textContent='ACHATS';
                                 optionEntiteDestination.value='ACHATS';
                                 entiteDestination.append(optionEntiteDestination);


 async function getAllEnterprises(){
         const urlSource=`${baseURL}/api/v1/enterprises/get-all`;
         const response=await fetch(urlSource, {
                                                 method: 'GET',
                                                 headers: {
                                                     'Content-Type': 'application/json'
                                                 }
                                             });
         if(!response.ok){

         }else{
         const data=await response.json();
         source.innerHTML="";
         let optionEnterprise=document.createElement('option');
           optionEnterprise.textContent=' Sélectionner une entreprise';
             optionEnterprise.value=' Sélectionner une entreprise';
             optionEnterprise.selected=true;
             optionEnterprise.disabled=true;
             source.append(optionEnterprise);

             data.forEach(enterprise=>{
             optionEnterprise=document.createElement('option');
             optionEnterprise.textContent=enterprise.name;
             optionEnterprise.value=enterprise.name;
              source.append(optionEnterprise);
             })
         }
        }
        getAllEnterprises();


}else if(entiteSource.value=="AGENCE"){
 entiteDestination.innerHTML='';
    let optionEntiteDestination=document.createElement('option');
    optionEntiteDestination.textContent=' Sélectionner une entité destination';
    optionEntiteDestination.value=' Sélectionner une entité destination';
    optionEntiteDestination.selected=true;
    optionEntiteDestination.disabled=true;
    entiteDestination.append(optionEntiteDestination);

    optionEntiteDestination=document.createElement('option');
        optionEntiteDestination.textContent='COMPAGNIE';
        optionEntiteDestination.value='COMPAGNIE';
        entiteDestination.append(optionEntiteDestination);

         optionEntiteDestination=document.createElement('option');
                optionEntiteDestination.textContent='CAISSE';
                optionEntiteDestination.value='CAISSE';
                entiteDestination.append(optionEntiteDestination);

                 optionEntiteDestination=document.createElement('option');
                 optionEntiteDestination.textContent='DEPENSE';
                 optionEntiteDestination.value='DEPENSE';
                 entiteDestination.append(optionEntiteDestination);


 async function getAllAgencies(){
         const urlSource=`${baseURL}/api/v1/agencies/get-all`;
         const response=await fetch(urlSource, {
                                                 method: 'GET',
                                                 headers: {
                                                     'Content-Type': 'application/json'
                                                 }
                                             });
         if(!response.ok){

         }else{
         const data=await response.json();
         source.innerHTML='';
         let optionAgency=document.createElement('option');
           optionAgency.textContent=' Sélectionner une agence';
             optionAgency.value=' Sélectionner une agence';
             optionAgency.selected=true;
             optionAgency.disabled=true;
             source.append(optionAgency);

             data.forEach(agency=>{
             optionAgency=document.createElement('option');
             optionAgency.textContent=agency.name;
             optionAgency.value=agency.name;
              source.append(optionAgency);
             })
         }
        }
        getAllAgencies();

}else if(entiteSource.value=="BANQUE"){
 entiteDestination.innerHTML='';
    let optionEntiteDestination=document.createElement('option');
    optionEntiteDestination.textContent=' Sélectionner une entité destination';
    optionEntiteDestination.value=' Sélectionner une entité destination';
    optionEntiteDestination.selected=true;
    optionEntiteDestination.disabled=true;
    entiteDestination.append(optionEntiteDestination);

    optionEntiteDestination=document.createElement('option');
        optionEntiteDestination.textContent='COMPAGNIE';
        optionEntiteDestination.value='COMPAGNIE';
        entiteDestination.append(optionEntiteDestination);



 async function getAllBankAccount(){
         const urlSource=`${baseURL}/api/v1/bank-account/get-all`;
         const response=await fetch(urlSource, {
                                                 method: 'GET',
                                                 headers: {
                                                     'Content-Type': 'application/json'
                                                 }
                                             });
         if(!response.ok){

         }else{
         const data=await response.json();
         source.innerHTML='';
         let optionAccount=document.createElement('option');
           optionAccount.textContent=' Sélectionner un compte';
             optionAccount.value=' Sélectionner un compte';
             optionAccount.selected=true;
             optionAccount.disabled=true;
             source.append(optionAccount);

             data.forEach(account=>{
             optionAccount=document.createElement('option');
             optionAccount.textContent=account.bankAccountNumber;
             optionAccount.value=account.bankAccountNumber;
              source.append(optionAccount);
             })
         }
        }
        getAllBankAccount();


}else if(entiteSource.value=="CAISSE"){
  entiteDestination.innerHTML='';
     let optionEntiteDestination=document.createElement('option');
     optionEntiteDestination.textContent=' Sélectionner une entité destination';
     optionEntiteDestination.value=' Sélectionner une entité destination';
     optionEntiteDestination.selected=true;
     optionEntiteDestination.disabled=true;
     entiteDestination.append(optionEntiteDestination);

     optionEntiteDestination=document.createElement('option');
         optionEntiteDestination.textContent='AGENCE';
         optionEntiteDestination.value='AGENCE';
         entiteDestination.append(optionEntiteDestination);


  async function getAllCashes(){
          const urlSource=`${baseURL}/api/v1/cashes/get-all`;
          const response=await fetch(urlSource, {
                                                  method: 'GET',
                                                  headers: {
                                                      'Content-Type': 'application/json'
                                                  }
                                              });
          if(!response.ok){

          }else{
          const data=await response.json();
          source.innerHTML='';
          let optionCash=document.createElement('option');
            optionCash.textContent=' Sélectionner une caisse';
              optionCash.value=' Sélectionner une caisse';
              optionCash.selected=true;
              optionCash.disabled=true;
              source.append(optionCash);

              data.forEach(cash=>{
              optionCash=document.createElement('option');
              optionCash.textContent=cash.refCash;
              optionCash.value=cash.refCash;
               source.append(optionCash);
              })
          }
         }
         getAllCashes();

 }else if(entiteSource.value=="ACHATS"){
    entiteDestination.innerHTML='';
       let optionEntiteDestination=document.createElement('option');
       optionEntiteDestination.textContent=' Sélectionner une entité destination';
       optionEntiteDestination.value=' Sélectionner une entité destination';
       optionEntiteDestination.selected=true;
       optionEntiteDestination.disabled=true;
       entiteDestination.append(optionEntiteDestination);

       optionEntiteDestination=document.createElement('option');
           optionEntiteDestination.textContent='COMPAGNIE';
           optionEntiteDestination.value='COMPAGNIE';
           entiteDestination.append(optionEntiteDestination);



            source.innerHTML='';
            let option=document.createElement('option');
              option.textContent=' Sélectionner l\'entité des achats';
                option.value=' Sélectionner l\'entité des achats';
                option.selected=true;
                option.disabled=true;
                source.append(option);


                option=document.createElement('option');
                option.textContent="BC0000-00-0000";
                option.value="BC0000-00-0000";
                 source.append(option);




   }else if(entiteSource.value=="VENTES"){
    entiteDestination.innerHTML='';
       let optionEntiteDestination=document.createElement('option');
       optionEntiteDestination.textContent=' Sélectionner une entité destination';
       optionEntiteDestination.value=' Sélectionner une entité destination';
       optionEntiteDestination.selected=true;
       optionEntiteDestination.disabled=true;
       entiteDestination.append(optionEntiteDestination);

       optionEntiteDestination=document.createElement('option');
           optionEntiteDestination.textContent='COMPAGNIE';
           optionEntiteDestination.value='COMPAGNIE';
           entiteDestination.append(optionEntiteDestination);

            source.innerHTML='';
            let option=document.createElement('option');
              option.textContent=' Sélectionner une entité';
                option.value=' Sélectionner une entité';
                option.selected=true;
                option.disabled=true;
                source.append(option);


                option=document.createElement('option');
                option.textContent="VENTES";
                option.value="VENTES";
                 source.append(option);

   }else if(entiteSource.value=="AUTRES VERSEMENTS"){
        entiteDestination.innerHTML='';
           let optionEntiteDestination=document.createElement('option');
           optionEntiteDestination.textContent=' Sélectionner une entité destination';
           optionEntiteDestination.value=' Sélectionner une entité destination';
           optionEntiteDestination.selected=true;
           optionEntiteDestination.disabled=true;
           entiteDestination.append(optionEntiteDestination);

           optionEntiteDestination=document.createElement('option');
               optionEntiteDestination.textContent='COMPAGNIE';
               optionEntiteDestination.value='COMPAGNIE';
               entiteDestination.append(optionEntiteDestination);

                source.innerHTML='';
                let option=document.createElement('option');
                  option.textContent=' Sélectionner une entité';
                    option.value=' Sélectionner une entité';
                    option.selected=true;
                    option.disabled=true;
                    source.append(option);


                    option=document.createElement('option');
                    option.textContent="AUTRES VERSEMENTS";
                    option.value="AUTRES VERSEMENTS";
                     source.append(option);

       }

})








entiteDestination.addEventListener('change',()=>{
destination.innerHTML='';
if(entiteDestination.value=="PAYS"){
    async function getAllCountries(){
    const urlCountries=`${baseURL}/api/v1/countries/get-all`;
    const response=await fetch(urlCountries, {
                                               method: 'GET',
                                               headers: {
                                                   'Content-Type': 'application/json'
                                               }
                                           });
    if(!response.ok){

    }else{
    const data=await response.json();
     let optionCountry=document.createElement('option');
                optionCountry.textContent=' Sélectionner un pays';
                  optionCountry.value=' Sélectionner un pays';
                  optionCountry.selected=true;
                  optionCountry.disabled=true;
                  destination.append(optionCountry);
                  data.forEach(country=>{
                  optionCountry=document.createElement('option');
                  optionCountry.textContent=country.name;
                  optionCountry.value=country.name;
                   destination.append(optionCountry);
                  })
    }
    }
    getAllCountries();


}else if(entiteDestination.value=="COMPAGNIE"){
     async function getAllCountries(){
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
                 optionEnterprise.textContent=' Sélectionner une entreprise';
                   optionEnterprise.value=' Sélectionner une entreprise';
                   optionEnterprise.selected=true;
                   optionEnterprise.disabled=true;
                   destination.append(optionEnterprise);
                   data.forEach(enterprise=>{
                   optionEnterprise=document.createElement('option');
                   optionEnterprise.textContent=enterprise.name;
                   optionEnterprise.value=enterprise.name;
                    destination.append(optionEnterprise);
                   })
     }
     }
     getAllCountries();

 }else if(entiteDestination.value=="BANQUE"){
       async function getAllAccounts(){
       const urlAccount=`${baseURL}/api/v1/bank-account/get-all`;
       const response=await fetch(urlAccount, {
                                                method: 'GET',
                                                headers: {
                                                    'Content-Type': 'application/json'
                                                }
                                            });
       if(!response.ok){

       }else{
       const data=await response.json();
        let optionAccount=document.createElement('option');
                   optionAccount.textContent=' Sélectionner un compte';
                     optionAccount.value=' Sélectionner un compte';
                     optionAccount.selected=true;
                     optionAccount.disabled=true;
                     destination.append(optionAccount);
                     data.forEach(account=>{
                     optionAccount=document.createElement('option');
                     optionAccount.textContent=account.bankAccountNumber;
                     optionAccount.value=account.bankAccountNumber;
                      destination.append(optionAccount);
                     })
       }
       }
       getAllAccounts();

   }else if(entiteDestination.value=="DEPENSE"){
                   async function getAllSpents(){
                   const urlSpent=`${baseURL}/api/v1/spents/get-all`;
                   const response=await fetch(urlSpent, {
                                                          method: 'GET',
                                                          headers: {
                                                              'Content-Type': 'application/json'
                                                          }
                                                      });
                   if(!response.ok){

                   }else{
                   const data=await response.json();
                    let optionSpent=document.createElement('option');
                               optionSpent.textContent=' Sélectionner une dépense';
                                 optionSpent.value=' Sélectionner une dépense';
                                 optionSpent.selected=true;
                                 optionSpent.disabled=true;
                                 destination.append(optionSpent);

                                 data.forEach(spent=>{
                                 optionSpent=document.createElement('option');
                                 optionSpent.textContent=spent.name;
                                 optionSpent.value=spent.name;
                                  destination.append(optionSpent);
                                 })
                   }
                   }
                   getAllSpents();

               }else if(entiteDestination.value=="AGENCE"){
           async function getAllAgencies(){
           const urlAgency=`${baseURL}/api/v1/agencies/get-all`;
           const response=await fetch(urlAgency, {
                                                   method: 'GET',
                                                   headers: {
                                                       'Content-Type': 'application/json'
                                                   }
                                               });
           if(!response.ok){

           }else{
           const data=await response.json();
            let optionAgency=document.createElement('option');
                       optionAgency.textContent=' Sélectionner un agence';
                         optionAgency.value=' Sélectionner un agence';
                         optionAgency.selected=true;
                         optionAgency.disabled=true;
                         destination.append(optionAgency);

                         data.forEach(agency=>{
                         optionAgency=document.createElement('option');
                         optionAgency.textContent=agency.name;
                         optionAgency.value=agency.name;
                          destination.append(optionAgency);
                         })
           }
           }
           getAllAgencies();

       }else if(entiteDestination.value=="CAISSE"){
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
                    let optionCash=document.createElement('option');
                               optionCash.textContent=' Sélectionner une caisse';
                                 optionCash.value=' Sélectionner une caisse';
                                 optionCash.selected=true;
                                 optionCash.disabled=true;
                                 destination.append(optionCash);

                                 data.forEach(cash=>{
                                 if(cash.agency==source.value){
                                 optionCash=document.createElement('option');
                                    optionCash.textContent=cash.refCash;
                                    optionCash.value=cash.refCash;
                                    destination.append(optionCash);
                                 }

                                 })
                   }
                   }
                   getAllCashes();

               }else if(entiteDestination.value=="ACHATS"){


               let option=document.createElement('option');
               option.textContent=' Sélectionner l\'entité des achats';
               option.value=' Sélectionner l\'entité des achats';
               option.selected=true;
               option.disabled=true;
               destination.append(option);

               option=document.createElement('option');
               option.textContent='BC0000-00-0000';
               option.value='BC0000-00-0000';
                destination.append(option);
 }


})


const startDateCredit=document.querySelector('#startDateCredit');
const endDateCredit=document.querySelector('#endDateCredit');
const searchMvtCredit=document.querySelector('#searchMvtCredit');
const tBodyTableMvtCredit=document.querySelector('#tableMvtCredit tbody')

searchMvtCredit.addEventListener('click',()=>{
async function getAllMvtCreditByDate(startDateCredit,endDateCredit){
const urlMvtCredit = `${baseURL}/api/v1/mvt-credits/get-all-by-date?startDateCredit=${startDateCredit}&endDateCredit=${endDateCredit}`;
const response=await fetch(urlMvtCredit, {
                                           method: 'GET',
                                           headers: {
                                               'Content-Type': 'application/json'
                                           }
                                       })
if(!response.ok){
}else{
const data=await response.json();
if(data.length>0){
data.forEach(mvtCredit=>{

let tr=document.createElement('tr');

let tdIdMvtCredit=document.createElement('td');
tdIdMvtCredit.textContent=mvtCredit.idMvtCredit;
tr.append(tdIdMvtCredit);

let tdDateCreation=document.createElement('td');
tdDateCreation.textContent=mvtCredit.dateCreation;
tr.append(tdDateCreation);

let tdSource=document.createElement('td');
tdSource.textContent=mvtCredit.source;
tr.append(tdSource);

let tdDestination=document.createElement('td');
tdDestination.textContent=mvtCredit.destination;
tr.append(tdDestination);

let tdAmount=document.createElement('td');
tdAmount.textContent=mvtCredit.amount;
tr.append(tdAmount);

let tdStatutCredit=document.createElement('td');
tdStatutCredit.textContent=mvtCredit.statutCredit;
tr.append(tdStatutCredit);


tBodyTableMvtCredit.append(tr);

})

}else{
      let tr=document.createElement('tr');
      tr.innerHTML="<td colspan='7'>Aucune donnée trouvée...</td>"
      tBodyTableMvtCredit.append(tr);
      }
}
}
getAllMvtCreditByDate(startDateCredit.value,endDateCredit.value)

})



const nameService=document.querySelector('#nameService');
const descriptionService=document.querySelector('#descriptionService');

nameService.addEventListener('change',()=>{
descriptionService.value=nameService.value;
})


const serviceEnterprise=document.querySelector('#serviceEnterprise');
const serviceCategory=document.querySelector('#serviceCategory');
const serviceName=document.querySelector('#serviceName');
const servicePrice=document.querySelector('#servicePrice');
const serviceDescription=document.querySelector('#serviceDescription');
const updateService=document.querySelector('#btn_update_service');


document.querySelector('#tableService tbody').addEventListener('click',()=>{
const rows = document.querySelectorAll('#tableService tbody tr');
rows.forEach(row => {
    row.addEventListener('click', function() {
     const refSellingService=row.children[2].innerText;
     async function getServiceByRef(){
     const urlSellingService=`${baseURL}/api/v1/selling-services/get-by-name/${refSellingService}`;
     const response=await fetch(urlSellingService, {
                                                     method: 'GET',
                                                     headers: {
                                                         'Content-Type': 'application/json'
                                                     }
                                                 });
     if(!response.ok){}else{
     const data=await response.json();

     serviceEnterprise.value=data.enterprise;
     serviceCategory.value=data.category;
     serviceName.value=data.name;
     servicePrice.value=data.price;
     serviceDescription.value=data.description;

     updateService.click();
     }
     }
     getServiceByRef();

    });
});
})




let selectCountryAgency=document.querySelector('#select_country_agency');
  let selectEnterpriseAgency=document.querySelector('#select_enterprise_agency');
  let selectRegionAgency=document.querySelector('#select_region_agency');
   let selectCityAgency=document.querySelector('#select_city_agency');

function getAllCountry(){
async function getAllCountriesAgency(){
  selectCountryAgency.innerHTML='';
 const urlCountry=`${baseURL}/api/v1/countries/get-all`;
  const response=await fetch(urlCountry, {
                                           method: 'GET',
                                           headers: {
                                               'Content-Type': 'application/json'
                                           }
                                       });
  if(!response.ok){
   }else{
    const data=await response.json();

    let optionCountry=document.createElement('option');
   optionCountry.textContent=' Selectionner un pays';
   optionCountry.value=' Selectionner un pays';
   optionCountry.selected=true;
   optionCountry.disabled=true;
   selectCountryAgency.append(optionCountry);

data.forEach(country=>{
optionCountry=document.createElement('option');
optionCountry.textContent=country.name;
 optionCountry.value=country.name;
selectCountryAgency.append(optionCountry);
})
}
}
getAllCountriesAgency();
}
getAllCountry()


selectCountryAgency.addEventListener('change',()=>{  async function getAllEnterprise(){
  selectEnterpriseAgency.innerHTML=''
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
selectEnterpriseAgency.append(optionEnterprise);

data.forEach(enterprise=>{
if(enterprise.country==selectCountryAgency.value){
let optionEnterprise=document.createElement('option');
optionEnterprise.textContent=enterprise.name;
optionEnterprise.value=enterprise.name;
selectEnterpriseAgency.append(optionEnterprise);
}
})
}
}
getAllEnterprise();
})



selectEnterpriseAgency.addEventListener('click',()=>{
selectRegionAgency.innerHTML='';

     let optionRegion=document.createElement('option');
       optionRegion.textContent=' Selectionner une région';
      optionRegion.value=' Selectionner une région';
     optionRegion.selected=true;
      optionRegion.disabled=true;
      selectRegionAgency.append(optionRegion);


 let optCentre=document.createElement('option');

    optCentre.textContent='CENTRE';
      optCentre.value='CENTRE';
       selectRegionAgency.append(optCentre);

 let optLittoral=document.createElement('option');

        optLittoral.textContent='LITTORAL';
             optLittoral.value='LITTORAL';
              selectRegionAgency.append(optLittoral);

 let optOuest=document.createElement('option');
               optOuest.textContent='OUEST';
                           optOuest.value='OUEST';
                            selectRegionAgency.append(optOuest);

 let optEst=document.createElement('option');
              optEst.textContent='EST';
                          optEst.value='EST';
                           selectRegionAgency.append(optEst);

 let optExtNord=document.createElement('option');
               optExtNord.textContent='EXTRÊME-NORD';
                           optExtNord.value='EXTRÊME-NORD';
                            selectRegionAgency.append(optExtNord);

 let optSud=document.createElement('option');
          optSud.textContent='SUD';
        optSud.value='SUD';
           selectRegionAgency.append(optSud);

 let optSudOuest=document.createElement('option');
                optSudOuest.textContent='SUD-OUEST';
                 optSudOuest.value='SUD-OUEST';
                    selectRegionAgency.append(optSudOuest);

 let optNordOuest=document.createElement('option');
         optNordOuest.textContent='NORD-OUEST';
          optNordOuest.value='NORD-OUEST';
             selectRegionAgency.append(optNordOuest);

 let optAdamaoua=document.createElement('option');
           optAdamaoua.textContent='ADAMAOUA';
            optAdamaoua.value='ADAMAOUA';
               selectRegionAgency.append(optAdamaoua);

 let optNord=document.createElement('option');
                 optNord.textContent='NORD';
                  optNord.value='NORD';
                     selectRegionAgency.append(optNord);


})

selectRegionAgency.addEventListener('change',()=>{async function getAllCities(){
selectCityAgency.innerHTML='';
                                                 const urlCity=`${baseURL}/api/v1/cities/get-all`;
                                                 const response=await fetch(urlCity, {
                                                                                       method: 'GET',
                                                                                       headers: {
                                                                                           'Content-Type': 'application/json'
                                                                                       }
                                                                                   });
                                                 if(!response.ok){
                                                 }else{
                                                 const data=await response.json();
                                                        let optionCity=document.createElement('option');
                                                       optionCity.textContent=' Selectionner une ville';
                                                      optionCity.value=' Selectionner une ville';
                                                       optionCity.selected=true;
                                                       optionCity.disabled=true;
                                                        selectCityAgency.append(optionCity);


                                                 data.forEach(city=>{
                                                 if(city.region==selectRegionAgency.value){
                                               optionCity=document.createElement('option');
                                               optionCity.textContent=city.name;
                                                optionCity.value=city.name;
                                                 selectCityAgency.append(optionCity);
                                                 }
                                                 })
                                                 }
                                                 }
                                getAllCities();
                                                } )



const divAgency=document.querySelector('#div_update_agency');
const divUser=document.querySelector('#div_update_user');
const divRefCash=document.querySelector('#div_update_refCash');

const selectEnterpriseCash=document.querySelector('#selectEnterpriseCash');
const selectAgencyCash=document.querySelector('#selectAgencyCash');

async function getAllCountriesAgency(){
const urlCountryAgency=`${baseURL}/api/v1/enterprises/get-all`;
const response=await fetch(urlCountryAgency, {
                                               method: 'GET',
                                               headers: {
                                                   'Content-Type': 'application/json'
                                               }
                                           })
if(!response.ok){
}else{
const data=await response.json();
selectEnterpriseCash.innerHTML='';
let option=document.createElement('option');
option.textContent=' Sélectionner une entreprise';
option.value=' Sélectionner une entreprise';
option.selected=true;
option.disabled=true;
selectEnterpriseCash.append(option);

data.forEach(enterprise=>{
option=document.createElement('option');
option.textContent=enterprise.name;
option.value=enterprise.name;
selectEnterpriseCash.append(option);
})

}
}
getAllCountriesAgency();


selectEnterpriseCash.addEventListener('change',()=>{
async function getAllagencies(){
const urlAgency=`${baseURL}/api/v1/agencies/get-all`;
const response=await fetch(urlAgency, {
                                        method: 'GET',
                                        headers: {
                                            'Content-Type': 'application/json'
                                        }
                                    });
if(!response.ok){}else{
const data=await response.json();
selectAgencyCash.innerHTML='';
let optionAgency=document.createElement('option');
optionAgency.textContent=' Selectionner une agence';
optionAgency.value=' Selectionner une agence';
optionAgency.selected=true
optionAgency.disabled=true;
selectAgencyCash.append(optionAgency);

data.forEach(agency=>{
if(selectEnterpriseCash.value==agency.enterprise){
optionAgency=document.createElement('option');
optionAgency.textContent=agency.name;
optionAgency.value=agency.name;
selectAgencyCash.append(optionAgency);
}

})
}
}
getAllagencies();
})


async function getAllagencies(){
const urlAgency=`${baseURL}/api/v1/agencies/get-all`;
const response=await fetch(urlAgency, {
                                        method: 'GET',
                                        headers: {
                                            'Content-Type': 'application/json'
                                        }
                                    });
if(!response.ok){}else{
const data=await response.json();
divAgency.innerHTML='';
let optionAgency=document.createElement('option');
optionAgency.textContent=' Selectionner une agence';
optionAgency.value=' Selectionner une agence';
optionAgency.selected=true
optionAgency.disabled=true;
divAgency.append(optionAgency);

data.forEach(agency=>{
optionAgency=document.createElement('option');
optionAgency.textContent=agency.name;
optionAgency.value=agency.name;
divAgency.append(optionAgency);

})
}
}
getAllagencies();


divAgency.addEventListener('change',()=>{
async function getAllUsers(){
const urlUser=`${baseURL}/api/v1/users/get-all`;
const response=await fetch(urlUser, {
                                      method: 'GET',
                                      headers: {
                                          'Content-Type': 'application/json'
                                      }
                                  });
if(!response.ok){

}else{
const data=await response.json();
divUser.innerHTML='';
let optionUser=document.createElement('option');
optionUser.textContent=' Selectionner un caissier';
optionUser.value=' Selectionner un caissier';
optionUser.selected=true
optionUser.disabled=true;
divUser.append(optionUser);

data.forEach(users=>{
if(users.agency==divAgency.value && users.enabled==true){
optionUser=document.createElement('option');
optionUser.textContent=users.username;
optionUser.value=users.username;
divUser.append(optionUser);
}

})
}
}
getAllUsers();
})

divAgency.addEventListener('change',()=>{
async function getAllRefCashes(){
const urlRefCash=`${baseURL}/api/v1/cashes/get-all`;
const response=await fetch(urlRefCash, {
                                         method: 'GET',
                                         headers: {
                                             'Content-Type': 'application/json'
                                         }
                                     });
if(!response.ok){
}else{
const data=await response.json();
divRefCash.innerHTML='';
let optionRefCash=document.createElement('option');
optionRefCash.textContent=' Selectionner une caisse';
optionRefCash.value=' Selectionner une caisse';
optionRefCash.selected=true
optionRefCash.disabled=true;
divRefCash.append(optionRefCash);

data.forEach(cashes=>{
if(cashes.agency==divAgency.value){
optionRefCash=document.createElement('option');
optionRefCash.textContent=cashes.refCash;
optionRefCash.value=cashes.refCash;
divRefCash.append(optionRefCash);
}
})
}
}
getAllRefCashes();
})


const enterpriseStore=document.querySelector('#enterpriseStore');
const agencyStore=document.querySelector('#agencyStore');


async function getAllCountries(){
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
     enterpriseStore.innerHTML='';
      let optionEnterprise=document.createElement('option');
                 optionEnterprise.textContent=' Sélectionner une entreprise';
                   optionEnterprise.value=' Sélectionner une entreprise';
                   optionEnterprise.selected=true;
                   optionEnterprise.disabled=true;
                   enterpriseStore.append(optionEnterprise);
                   data.forEach(enterprise=>{
                   optionEnterprise=document.createElement('option');
                   optionEnterprise.textContent=enterprise.name;
                   optionEnterprise.value=enterprise.name;
                    enterpriseStore.append(optionEnterprise);
                   })
     }
     }
     getAllCountries();


enterpriseStore.addEventListener('change',()=>{
async function getAllagencies(){
const urlAgency=`${baseURL}/api/v1/agencies/get-all`;
const response=await fetch(urlAgency, {
                                        method: 'GET',
                                        headers: {
                                            'Content-Type': 'application/json'
                                        }
                                    });
if(!response.ok){}else{
const data=await response.json();
agencyStore.innerHTML='';
let optionAgency=document.createElement('option');
optionAgency.textContent=' Selectionner une agence';
optionAgency.value=' Selectionner une agence';
optionAgency.selected=true
optionAgency.disabled=true;
agencyStore.append(optionAgency);

data.forEach(agency=>{
if(enterpriseStore.value==agency.enterprise){
optionAgency=document.createElement('option');
optionAgency.textContent=agency.name;
optionAgency.value=agency.name;
agencyStore.append(optionAgency);
}

})
}
}
getAllagencies();
})


const enterpriseUpdateStore=document.querySelector('#enterpriseUpdateStore');
const agencyUpdateStore=document.querySelector('#agencyUpdateStore');
const storeUpdateStore=document.querySelector('#storeUpdateStore');
const nameUpdateStore=document.querySelector('#nameUpdateStore');
const userUpdateStore=document.querySelector('#userUpdateStore');

async function getAllEntrepriseStore(){
const urlEnterpriseStore=`${baseURL}/api/v1/enterprises/get-all`;
const response=await fetch(urlEnterpriseStore, {
                                                 method: 'GET',
                                                 headers: {
                                                     'Content-Type': 'application/json'
                                                 }
                                             })
if(!response.ok){
}else{
const data=await response.json();
enterpriseUpdateStore.innerHTML='';
let option=document.createElement('option');
option.textContent=' Sélectionner une entreprise';
option.value=' Sélectionner une entreprise';
option.selected=true;
option.disabled=true;
enterpriseUpdateStore.append(option);

data.forEach(enterprise=>{
option=document.createElement('option');
option.textContent=enterprise.name;
option.value=enterprise.name;
enterpriseUpdateStore.append(option);
})

}
}
getAllEntrepriseStore();



enterpriseUpdateStore.addEventListener('change',()=>{
async function getAllagencies(){
const urlAgency=`${baseURL}/api/v1/agencies/get-all`;
const response=await fetch(urlAgency, {
                                        method: 'GET',
                                        headers: {
                                            'Content-Type': 'application/json'
                                        }
                                    });
if(!response.ok){}else{
const data=await response.json();
agencyUpdateStore.innerHTML='';
let optionAgency=document.createElement('option');
optionAgency.textContent=' Selectionner une agence';
optionAgency.value=' Selectionner une agence';
optionAgency.selected=true
optionAgency.disabled=true;
agencyUpdateStore.append(optionAgency);


data.forEach(agency=>{
if(enterpriseUpdateStore.value==agency.enterprise){
optionAgency=document.createElement('option');
optionAgency.textContent=agency.name;
optionAgency.value=agency.name;
agencyUpdateStore.append(optionAgency);
}


})
}
}
getAllagencies();
})


agencyUpdateStore.addEventListener('change',()=>{
async function getAllStores(){
const urlStore=`${baseURL}/api/v1/stores/get-all`;
const response=await fetch(urlStore, {
                                       method: 'GET',
                                       headers: {
                                           'Content-Type': 'application/json'
                                       }
                                   });
if(!response.ok){}else{
const data=await response.json();
storeUpdateStore.innerHTML='';
let optionStore=document.createElement('option');
optionStore.textContent=' Selectionner un magasin';
optionStore.value=' Selectionner un magasin';
optionStore.selected=true
optionStore.disabled=true;
storeUpdateStore.append(optionStore);

data.forEach(store=>{
if(agencyUpdateStore.value==store.agencies){
optionStore=document.createElement('option');
optionStore.textContent=store.refStore;
optionStore.value=store.refStore;
storeUpdateStore.append(optionStore);
}

})
}
}
getAllStores();


async function getAllUserByAgency(){
const url=`${baseURL}/api/v1/users/get-all`
const response=await fetch(url, {
                                  method: 'GET',
                                  headers: {
                                      'Content-Type': 'application/json'
                                  }
                              });
if(!response.ok){
}else{
const data=await response.json();
userUpdateStore.innerHTML='';
let option=document.querySelector('option');
option.textContent=' Sélectionner un utilisateur';
option.value=' Sélectionner un utilisateur';
option.selected=true;
option.disabled=true;
userUpdateStore.append(option);

data.forEach(user=>{
if(user.agency != null){
if(user.agency==agencyUpdateStore.value && user.role[0] != 'ADMIN' && user.role[0] != 'SUPERADMIN'){
option=document.createElement('option');
option.textContent=user.username;
option.value=user.username;
userUpdateStore.append(option);
}
}
})
}

}

getAllUserByAgency();

})




storeUpdateStore.addEventListener('change',()=>{
async function getStoreByRef(){
const url=`${baseURL}/api/v1/stores/get-by-ref/${storeUpdateStore.value}`;
const response=await fetch(url, {
                                  method: 'GET',
                                  headers: {
                                      'Content-Type': 'application/json'
                                  }
                              });
if(!response.ok){
}else{
const data=await response.json();
nameUpdateStore.value=data.name;
}
}
getStoreByRef();
})



const labelCheck=document.querySelector("#labelCheck");
const check=document.querySelector("#check");
form_user=document.querySelector("#form-user");
form_store=document.querySelector("#form-store");
form_store.style.display='inline-block';
form_user.style.display='none'

check.addEventListener('change',()=>{
if(check.checked==true){
labelCheck.innerHTML='Nouvelle Affectation';
form_user.style.display='inline-block';
form_store.style.display='none'
}else{
labelCheck.innerHTML='Transfert inter-magasin';
form_user.style.display='none';
form_store.style.display='inline-block';
}
})


const enterpriseUser = document.querySelector("#enterpriseUser");
const agenceUser= document.querySelector("#agencyUser");


async function getAllEnterpriseUser(){
const url=`${baseURL}/api/v1/enterprises/get-all`
const response=await fetch(url, {
                                  method: 'GET',
                                  headers: {
                                      'Content-Type': 'application/json'
                                  }
                              });
if(!response.ok){
}else{
const data=await response.json();
enterpriseUser.innerHTML='';
let optionEnterprise=document.createElement("option");
optionEnterprise.textContent=' Sélectionner une entreprise';
optionEnterprise.value=' Sélectionner une entreprise';
optionEnterprise.selected=true;
optionEnterprise.disabled=true;
enterpriseUser.append(optionEnterprise);

data.forEach(country=>{
optionEnterprise=document.createElement("option");
optionEnterprise.textContent=country.name;
optionEnterprise.value=country.name;
enterpriseUser.append(optionEnterprise);
})


}
}

getAllEnterpriseUser();


enterpriseUser.addEventListener('change',()=>{
async function getAllAgencies(){
const urlAgency=`${baseURL}/api/v1/agencies/get-all`;
const response=await fetch(urlAgency, {
                                        method: 'GET',
                                        headers: {
                                            'Content-Type': 'application/json'
                                        }
                                    });
if(!response.ok){
}else{
const data=await response.json();
agenceUser.innerHTML='';
let optionAgency=document.createElement('option');
optionAgency.textContent=' Sélectionner une agence';
optionAgency.value=' Sélectionner une agence';
optionAgency.selected=true;
optionAgency.disabled=true;
agenceUser.append(optionAgency);

data.forEach(agency=>{
if(enterpriseUser.value==agency.enterprise){
optionAgency=document.createElement("option");
optionAgency.textContent=agency.name;
optionAgency.value=agency.name;
agenceUser.append(optionAgency);
}
})
}
}
getAllAgencies();
})





const selectEnterpriseUser=document.querySelector('#selectEnterpriseUser');
const user_name=document.querySelector('#username');
const fullNameUser=document.querySelector('#nomComplet');
const telephone=document.querySelector('#telephone');
const selectRole=document.querySelector('#selectRole');
const actif=document.querySelector('#actif');
const actualRole=document.querySelector('#actualRole');


document.querySelector('#table_user tbody').addEventListener('click',()=>{
const rows = document.querySelectorAll('#table_user tbody tr');
rows.forEach(row => {
    row.addEventListener('click', function() {
     const username=row.children[2].innerText;
     async function getUserById(){
     const urlUser=`${baseURL}/api/v1/users/get-by-username/${username}`;
     const response=await fetch(urlUser, {
                                           method: 'GET',
                                           headers: {
                                               'Content-Type': 'application/json'
                                           }
                                       });
     if(!response.ok){}else{
     const data=await response.json();

     selectEnterpriseUser.value=data.enterprise;
     user_name.value=data.username;
     fullNameUser.value=data.fullName;

     telephone.value=data.phoneNumber;

    if(data.enabled){
    actif.checked=true;
    }else{
      actif.checked=false;
    }
actualRole.value=data.role;
     document.querySelector('#btn_update').click();
     }
     }
     getUserById();

    });
});
})


const agencysUpdateStore=document.querySelector('#agencysUpdateStore');
const usersUpdateStore=document.querySelector('#usersUpdateStore');
const store1=document.querySelector('#store1');
const store2=document.querySelector('#store2');


async function getAllAgenciesUpdateStore(){
const urlAgency=`${baseURL}/api/v1/agencies/get-all`;
const response=await fetch(urlAgency, {
                                        method: 'GET',
                                        headers: {
                                            'Content-Type': 'application/json'
                                        }
                                    });
if(!response.ok){
}else{
const data=await response.json();
agencysUpdateStore.innerHTML='';
let optionAgency=document.createElement('option');
optionAgency.textContent=' Sélectionner une agence';
optionAgency.value=' Sélectionner une agence';
optionAgency.selected=true;
optionAgency.disabled=true;
agencysUpdateStore.append(optionAgency);

data.forEach(agency=>{
optionAgency=document.createElement("option");
optionAgency.textContent=agency.name;
optionAgency.value=agency.name;
agencysUpdateStore.append(optionAgency);
})
}
}
getAllAgenciesUpdateStore();


agencysUpdateStore.addEventListener('change',()=>{
async function getAllUserByAgency(){
const url=`${baseURL}/api/v1/users/get-all`
const response=await fetch(url, {
                                  method: 'GET',
                                  headers: {
                                      'Content-Type': 'application/json'
                                  }
                              });
if(!response.ok){
}else{
const data=await response.json();
usersUpdateStore.innerHTML='';
let option=document.querySelector('option');
option.textContent=' Sélectionner un utilisateur';
option.value=' Sélectionner un utilisateur';
option.selected=true;
option.disabled=true;
usersUpdateStore.append(option);


data.forEach(user=>{
if(user.agency != null){
if(user.agency==agencysUpdateStore.value && user.role[0] != 'ADMIN'){
option=document.createElement('option');
option.textContent=user.username;
option.value=user.username;
usersUpdateStore.append(option);
}
}
})

}

}
getAllUserByAgency();


async function getAllStores(){
const urlStore=`${baseURL}/api/v1/stores/get-all`;
const response=await fetch(urlStore, {
                                       method: 'GET',
                                       headers: {
                                           'Content-Type': 'application/json'
                                       }
                                   });
if(!response.ok){}else{
const data=await response.json();
store2.innerHTML='';
let optionStore=document.createElement('option');
optionStore.textContent=' Selectionner un magasin';
optionStore.value=' Selectionner un magasin';
optionStore.selected=true
optionStore.disabled=true;
store2.append(optionStore);

data.forEach(store=>{
if(agencysUpdateStore.value==store.agencies && store.refStore!=store1.value){
optionStore=document.createElement('option');
optionStore.textContent=store.refStore;
optionStore.value=store.refStore;
store2.append(optionStore);
}

})
}
}
getAllStores();
})


usersUpdateStore.addEventListener('change',()=>{
async function getAllStoreUsers(){
const url=`${baseURL}/api/v1/users/get-by-username/${usersUpdateStore.value}`;
const response=await fetch(url, {
                                  method: 'GET',
                                  headers: {
                                      'Content-Type': 'application/json'
                                  }
                              });
if(!response.ok){
}else{
const data=await response.json();
store1.value=data.store;
}
}
getAllStoreUsers();



async function getAllStores(){
const urlStore=`${baseURL}/api/v1/stores/get-all`;
const response=await fetch(urlStore, {
                                       method: 'GET',
                                       headers: {
                                           'Content-Type': 'application/json'
                                       }
                                   });
if(!response.ok){}else{
const data=await response.json();
store2.innerHTML='';
let optionStore=document.createElement('option');
optionStore.textContent=' Selectionner un magasin';
optionStore.value=' Selectionner un magasin';
optionStore.selected=true
optionStore.disabled=true;
store2.append(optionStore);

data.forEach(store=>{
if(store1.value!=store.refStore && store.refStore!="STORE01" && store.agencies==agencysUpdateStore.value){
optionStore=document.createElement('option');
optionStore.textContent=store.refStore;
optionStore.value=store.refStore;
store2.append(optionStore);
}

})
}
}
getAllStores();

})

const tableProduct=document.querySelector('#table_product');
const name=document.querySelector('#name');
const purchasePrice=document.querySelector('#purchasePrice');
const sellingPrice=document.querySelector('#sellingPrice');
const refProduct=document.querySelector('#refProduct');

document.querySelector('#table_product tbody').addEventListener('click',()=>{
const rows = document.querySelectorAll('#table_product tbody tr');
rows.forEach(row => {
    row.addEventListener('click', function() {
     const productName=row.children[0].innerText;
     async function getProductByName(){
     const urlProduct=`${baseURL}/api/v1/products/get-by-ref/${productName}`;
     const response=await fetch(urlProduct, {
                                              method: 'GET',
                                              headers: {
                                                  'Content-Type': 'application/json'
                                              }
                                          });
     if(!response.ok){}else{
     const data=await response.json();

     name.value=data.name;
     purchasePrice.value=data.purchasePrice;
     sellingPrice.value=data.sellingPrice;
     refProduct.value=data.refProduct;

     document.querySelector('#id_product').click();
     }
     }
     getProductByName();

    });
});
})



}else{
containerDashboard.className="d-none"
}



viewPassword.addEventListener('click',()=>{
             containerEnterprise.style.display='none';
             containerEnterprise.className='container w-75 d-none';
             containerAgency.style.display='none';
             containerAgency.className='container w-75 d-none';
             containerPassword.style.display='none';
             containerUser.style.display='none';
             containerUser.className='container w-75 d-none';
             containerPassword.style.display='inline-block';
              containerPassword.className='container w-75 d-flex flex-column';
             })






const startDate=document.querySelector('#startDate');
const endDate=document.querySelector('#endDate');
const btnSearch=document.querySelector('#btn_search');
const tbodyCr=document.querySelector('#table_crCash tbody');


btnSearch.addEventListener('click',()=>{
async function getAllcrCashes(){
const url=`${baseURL}/api/v1/cr-cash/get-by-date?startDate=${startDate.value}&endDate=${endDate.value}`;
const response=await fetch(url, {
                                  method: 'GET',
                                  headers: {
                                      'Content-Type': 'application/json'
                                  }
                              });
if(!response.ok){
}else{
const data=await response.json();
if(data!=null){
tbodyCr.innerHTML='';

data.forEach(crCash=>{
let tr=document.createElement('tr');


let td=document.createElement('td');
td.textContent=crCash.idCrCash;
tr.append(td);

td=document.createElement('td');
td.textContent=crCash.dateCrCash;
tr.append(td);

td=document.createElement('td');
td.textContent=crCash.agency;
tr.append(td);

td=document.createElement('td');
td.textContent=crCash.cash;
tr.append(td);

td=document.createElement('td');
td.textContent=crCash.soldeInitial;
tr.append(td);

td=document.createElement('td');
td.textContent=crCash.encaissement;
tr.append(td);

td=document.createElement('td');
td.textContent=crCash.decaissement;
tr.append(td);

td=document.createElement('td');
td.textContent=crCash.soldeFinal;
tr.append(td);

td=document.createElement('td');
td.textContent=crCash.totalCash;
tr.append(td);

td=document.createElement('td');
td.textContent=crCash.difference;
tr.append(td);

tbodyCr.append(tr);

})


}
}
}
getAllcrCashes();
})


const vueCash=document.querySelector('#vueCash');

tbodyCr.addEventListener('click', () => {
  const rows = document.querySelectorAll('#table_crCash tbody tr');
  rows.forEach(row => {
    row.addEventListener('click', function() {
      const idCrCash = row.children[0].innerText;
      async function getCrCashById() {
        const urlCrCash = `${baseURL}/api/v1/cr-cash/get-by-id/${idCrCash}`;
        const response = await fetch(urlCrCash, {
                                                  method: 'GET',
                                                  headers: {
                                                      'Content-Type': 'application/json'
                                                  }
                                              });

        if (response.ok) {
          const data = await response.json();


          document.querySelector('#idCrCash').value = data.idCrCash;
          document.querySelector('#agency').value = data.agency;
          document.querySelector('#cash').value = data.cash;
          document.querySelector('#dateCrCash').value = data.dateCrCash;

document.querySelector('#vueCash').click();


        }
      }

      getCrCashById();
    });
  });
});

const btnUpdateUser=document.querySelector('#btn_update_user');

btnUpdateUser.addEventListener('click',(e)=>{
if(selectRole.value=='Sélectionner un rôle'){
e.preventDefault();
alert("Rôle non sélectionné")
}else{
btnUpdateUser.submit();
}
})



document.querySelector("#btn-update-casher").addEventListener('click',(e)=>{
if(div_update_user.value==" Selectionner un utilisateur"){
e.preventDefault();
alert("Utilisateur non sélectionné");
}
})


const agencyEdit=document.querySelector('#agencyEdit');
const cashEdit=document.querySelector('#cashEdit');
const balance=document.querySelector('#balance');


agencyEdit.addEventListener('change',()=>{
 async function getAllCashes() {
        const urlCash = `${baseURL}/api/v1/cashes/get-all`;
        const response = await fetch(urlCash, {
                                                  method: 'GET',
                                                  headers: {
                                                      'Content-Type': 'application/json'
                                                  }
                                              });

        if (response.ok) {
          const data = await response.json();
data.forEach(cash=>{
cashEdit.innerHTML=''
 let option=document.createElement('option');
 option.textContent='Sélectionner une caisse';
  option.value='Sélectionner une caisse';
 option.selected=true;
 option.disabled=true;
 cashEdit.append(option);
 data.forEach(cash=>{
  option=document.createElement('option');
  option.textContent=cash.refCash;
    option.value=cash.refCash;
   cashEdit.append(option);
 })


})
        }
      }

      getAllCashes();
})

cashEdit.addEventListener('change',()=>{
async function getbalance(){
const urlCash=`${baseURL}/api/v1/cashes/get-by-cash/${cashEdit.value}`
const response=await fetch(urlCash ,{
  method: 'GET',
  headers: {
  'Content-Type': 'application/json'
  }
})
if(response.ok){
const data=await response.json();
balance.value=data.balance;
}
}
getbalance()
})

const btnBalance=document.querySelector('#btn_update_balance');

btnBalance.addEventListener('click',(e)=>{
if(agancyEdit.value=="Sélectionner une agence"){
e.preventDefault();
alert("Agence non sélectionnée");
}

})






