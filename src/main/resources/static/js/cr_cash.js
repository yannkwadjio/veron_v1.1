const baseURL="/veron"
const nb10000=document.querySelector('#nb10000');
const nb5000=document.querySelector('#nb5000');
const nb2000=document.querySelector('#nb2000');
const nb1000=document.querySelector('#nb1000');
const nb500=document.querySelector('#nb500');
const nb100=document.querySelector('#nb100');
const nb50=document.querySelector('#nb50');
const nb25=document.querySelector('#nb25');
const nb10=document.querySelector('#nb10');
const nb5=document.querySelector('#nb5');
const nb2=document.querySelector('#nb2');
const nb1=document.querySelector('#nb1');


const sum10000=document.querySelector('#sum10000');
const sum5000=document.querySelector('#sum5000');
const sum2000=document.querySelector('#sum2000');
const sum1000=document.querySelector('#sum1000');
const sum500=document.querySelector('#sum500');
const sum100=document.querySelector('#sum100');
const sum50=document.querySelector('#sum50');
const sum25=document.querySelector('#sum25');
const sum10=document.querySelector('#sum10');
const sum5=document.querySelector('#sum5');
const sum2=document.querySelector('#sum2');
const sum1=document.querySelector('#sum1');


const soldePhysique=document.querySelector("#soldePhysique");
const ecart=document.querySelector("#ecart");
const soldeFinal=document.querySelector("#soldeFinal");
const soldeTheorique=document.querySelector("#soldeFinalOk");
const totalEncaissement=document.querySelector("#totalEncaissement");
const totalDecaissement=document.querySelector("#totalDecaissement");
const soldeInitial=document.querySelector("#soldeInitial");
const responsible=document.querySelector('#responsible');

const refCash=document.querySelector('#refCash');
cashId.value=refCash.innerText;

async function getResponsibleByRefCash(){
const urlResponsible=`${baseURL}/api/v1/users/get-by-cash/${cashId.value}`;
const response=await fetch(urlResponsible, {
                                             method: 'GET',
                                             headers: {
                                                 'Content-Type': 'application/json'
                                             }
                                         });
if(!response.ok){
}else{
const data=await response.json();
responsible.value=data.fullName;
}
}
getResponsibleByRefCash();

nb10000.addEventListener('change',()=>{
sum10000.value=nb10000.value*10000
soldePhysique.value=Number(sum10000.value)+Number(sum5000.value)+Number(sum2000.value)+Number(sum1000.value)+Number(sum500.value)+Number(sum100.value)+Number(sum50.value)+Number(sum25.value)+Number(sum10.value)+Number(sum5.value)+Number(sum2.value)+Number(sum1.value)
ecart.value=Number(soldePhysique.value)-Number(soldeTheorique.value);
})

nb5000.addEventListener('change',()=>{
sum5000.value=nb5000.value*5000
soldePhysique.value=Number(sum10000.value)+Number(sum5000.value)+Number(sum2000.value)+Number(sum1000.value)+Number(sum500.value)+Number(sum100.value)+Number(sum50.value)+Number(sum25.value)+Number(sum10.value)+Number(sum5.value)+Number(sum2.value)+Number(sum1.value)
ecart.value=Number(soldePhysique.value)-Number(soldeTheorique.value);
})

nb2000.addEventListener('change',()=>{
sum2000.value=nb2000.value*2000
soldePhysique.value=Number(sum10000.value)+Number(sum5000.value)+Number(sum2000.value)+Number(sum1000.value)+Number(sum500.value)+Number(sum100.value)+Number(sum50.value)+Number(sum25.value)+Number(sum10.value)+Number(sum5.value)+Number(sum2.value)+Number(sum1.value)
ecart.value=Number(soldePhysique.value)-Number(soldeTheorique.value);
})

nb1000.addEventListener('change',()=>{
sum1000.value=nb1000.value*1000
soldePhysique.value=Number(sum10000.value)+Number(sum5000.value)+Number(sum2000.value)+Number(sum1000.value)+Number(sum500.value)+Number(sum100.value)+Number(sum50.value)+Number(sum25.value)+Number(sum10.value)+Number(sum5.value)+Number(sum2.value)+Number(sum1.value)
ecart.value=Number(soldePhysique.value)-Number(soldeTheorique.value);
})

nb500.addEventListener('change',()=>{
sum500.value=nb500.value*500
soldePhysique.value=Number(sum10000.value)+Number(sum5000.value)+Number(sum2000.value)+Number(sum1000.value)+Number(sum500.value)+Number(sum100.value)+Number(sum50.value)+Number(sum25.value)+Number(sum10.value)+Number(sum5.value)+Number(sum2.value)+Number(sum1.value)
ecart.value=Number(soldePhysique.value)-Number(soldeTheorique.value);
})

nb100.addEventListener('change',()=>{
sum100.value=nb100.value*100
soldePhysique.value=Number(sum10000.value)+Number(sum5000.value)+Number(sum2000.value)+Number(sum1000.value)+Number(sum500.value)+Number(sum100.value)+Number(sum50.value)+Number(sum25.value)+Number(sum10.value)+Number(sum5.value)+Number(sum2.value)+Number(sum1.value)
ecart.value=Number(soldePhysique.value)-Number(soldeTheorique.value);
})

nb50.addEventListener('change',()=>{
sum50.value=nb50.value*50
soldePhysique.value=Number(sum10000.value)+Number(sum5000.value)+Number(sum2000.value)+Number(sum1000.value)+Number(sum500.value)+Number(sum100.value)+Number(sum50.value)+Number(sum25.value)+Number(sum10.value)+Number(sum5.value)+Number(sum2.value)+Number(sum1.value)
ecart.value=Number(soldePhysique.value)-Number(soldeTheorique.value);
})

nb25.addEventListener('change',()=>{
sum25.value=nb25.value*25
soldePhysique.value=Number(sum10000.value)+Number(sum5000.value)+Number(sum2000.value)+Number(sum1000.value)+Number(sum500.value)+Number(sum100.value)+Number(sum50.value)+Number(sum25.value)+Number(sum10.value)+Number(sum5.value)+Number(sum2.value)+Number(sum1.value)
ecart.value=Number(soldePhysique.value)-Number(soldeTheorique.value);
})

nb10.addEventListener('change',()=>{
sum10.value=nb10.value*10
soldePhysique.value=Number(sum10000.value)+Number(sum5000.value)+Number(sum2000.value)+Number(sum1000.value)+Number(sum500.value)+Number(sum100.value)+Number(sum50.value)+Number(sum25.value)+Number(sum10.value)+Number(sum5.value)+Number(sum2.value)+Number(sum1.value)
ecart.value=Number(soldePhysique.value)-Number(soldeTheorique.value);
})

nb5.addEventListener('change',()=>{
sum5.value=nb5.value*5
soldePhysique.value=Number(sum10000.value)+Number(sum5000.value)+Number(sum2000.value)+Number(sum1000.value)+Number(sum500.value)+Number(sum100.value)+Number(sum50.value)+Number(sum25.value)+Number(sum10.value)+Number(sum5.value)+Number(sum2.value)+Number(sum1.value)
ecart.value=Number(soldePhysique.value)-Number(soldeTheorique.value);
})

nb2.addEventListener('change',()=>{
sum2.value=nb2.value*2
soldePhysique.value=Number(sum10000.value)+Number(sum5000.value)+Number(sum2000.value)+Number(sum1000.value)+Number(sum500.value)+Number(sum100.value)+Number(sum50.value)+Number(sum25.value)+Number(sum10.value)+Number(sum5.value)+Number(sum2.value)+Number(sum1.value)
ecart.value=Number(soldePhysique.value)-Number(soldeTheorique.value);
})

nb1.addEventListener('change',()=>{
sum1.value=nb1.value*1
soldePhysique.value=Number(sum10000.value)+Number(sum5000.value)+Number(sum2000.value)+Number(sum1000.value)+Number(sum500.value)+Number(sum100.value)+Number(sum50.value)+Number(sum25.value)+Number(sum10.value)+Number(sum5.value)+Number(sum2.value)+Number(sum1.value)
ecart.value=Number(soldePhysique.value)-Number(soldeTheorique.value);
})

var enc=0;
var dec=0;

const today = new Date();
const year = today.getFullYear();
const month = String(today.getMonth() + 1).padStart(2, '0');
const day = String(today.getDate()).padStart(2, '0');
const dte=`${year}-${month}-${day}`
const nomUtil=document.querySelector('#nomUtil')

async function calculSolde(){
const url=`${baseURL}/api/v1/mvt-cash/get-all`;
const response=await fetch(url, {
                                  method: 'GET',
                                  headers: {
                                      'Content-Type': 'application/json'
                                  }
                              });
if(!response.ok){
}else{
const data=await response.json();
data.forEach(mvt=>{
if(mvt.dateMvtCash==dte){
if(mvt.sens=="ENCAISSEMENT" && mvt.userCreated==nomUtil.innerText){
enc+=mvt.amount+mvt.fee;
totalEncaissement.value=enc;
soldeFinal.value=Number(soldeInitial.value)+Number(totalEncaissement.value)-Number(totalDecaissement.value);
soldeFinalOk.value=Number(soldeFinal.value);
ecart.value=Number(soldePhysique.value)-Number(soldeFinalOk.value);

}else if(mvt.sens=="DECAISSEMENT" && mvt.userCreated==nomUtil.innerText){
dec+=mvt.amount+mvt.fee;
totalDecaissement.value=dec;
soldeFinal.value=Number(soldeInitial.value)+Number(totalEncaissement.value)-Number(totalDecaissement.value);
soldeFinalOk.value=Number(soldeFinal.value);
ecart.value=Number(soldePhysique.value)-Number(soldeFinalOk.value);
}
}
})
}
}
calculSolde();



async function getSoldeInitial(){
const urlCrCash=`${baseURL}/api/v1/cr-cash/get-all`;
const response=await fetch(urlCrCash, {
                                        method: 'GET',
                                        headers: {
                                            'Content-Type': 'application/json'
                                        }
                                    });
if(!response.ok){
}else{
const data=await response.json();
data.forEach(crCash=>{
if(crCash.dateCrCash!=dte && crCash.cash==refCash.innerText){
soldeInitial.value=Number(crCash.totalCash);
soldeFinal.value=Number(soldeInitial.value)+Number(totalEncaissement.value)-Number(totalDecaissement.value);
soldeFinalOk.value=Number(soldeFinal.value);
ecart.value=Number(soldePhysique.value)-Number(soldeFinalOk.value);
}
})
}

}
getSoldeInitial();










