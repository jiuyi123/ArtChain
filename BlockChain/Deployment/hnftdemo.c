#include"vntlib.h"
KEY string hello;
KEY string NAME;
KEY string SYMBOL="ddd";

//查询address账户中代币的数量
KEY mapping(address, uint256) balances;

//查询代币所有者
KEY mapping(uint256, address) tokenOwners;

//查询代币编号是否已经存在
KEY mapping(uint256 , bool) tokenExists;

//
KEY mapping(address , mapping (address, uint256)) allowed;
KEY mapping(address , mapping(uint256, uint256)) ownerTokens;


EVENT Transfer(address from,address to,uint256 tokenId);
EVENT Approval(address oldOwner,address newOwner,uint256 tokenId);


constructor hnftdemo()
{
    hello = "hello vnt";
    NAME = "ddd";
    SYMBOL = "ddd";
}

UNMUTABLE
string getHello()
{
    return hello;
}

UNMUTABLE
string getSymbol(){
    return SYMBOL;
}

UNMUTABLE
string getName(){
        return NAME;
}

UNMUTABLE
uint256 balanceOf(address owner){
        balances.key = owner;
        return balances.value;
}


UNMUTABLE
address ownerOf(uint256 tokenId){
        tokenExists.key = tokenId;
        Assert(tokenExists.value,"");
        tokenOwners.key = tokenId;
        return tokenOwners.value;
}

MUTABLE
void approve(address to,uint256 tokenId){
        if(GetSender()==ownerOf(tokenId)&&GetSender()!=to){

                //allowed[GetSender()][to] = tokenId;
                allowed.key = GetSender();
                allowed.value.key = to;
                allowed.value.value = tokenId;

                Approval(GetSender(),to,tokenId);
        }
}

MUTABLE
void takeOwnership(uint256 tokenId){

        tokenExists.key = tokenId;
        if(tokenExists.key)
        {
                address oldOwner = ownerOf(tokenId);
                address newOwner = GetSender();
                if(oldOwner !=newOwner)
                {
                        allowed.key = oldOwner;
                        allowed.value.key = newOwner;
                        if(allowed.value.value == tokenId){

                                //balances[oldOwner] -= 1;
                                balances.key = oldOwner;
                                balances.value -= 1;

                                //tokenOwners[tokenId] = newOwner;
                                tokenOwners.key = tokenId;
                                tokenOwners.value = newOwner;

                                //balances[newOwner] += 1;
                                balances.key = newOwner;
                                balances.value += 1;

                                Transfer(oldOwner,newOwner,tokenId);
                        }

                }
        }
}

MUTABLE
void removeFromTokenList(address owner , uint256 tokenId){
        ownerTokens.key = owner;
        uint256 i = 0;
        ownerTokens.value.key = i;

        while(ownerTokens.value.value!=tokenId)
        {
                ownerTokens.value.value=0;

                i++;
                ownerTokens.value.key = i;
        }
        //while(ownerTokens.value.value!=tokenId)
        // for(uint256 i =0; ownerTokens[owner][i]!=tokenId;i++){
        //      ownerTokens[owner][i] = 0;
        // }
}

MUTABLE
void transfer(address to,uint256 tokenId){
        address currentOwner = GetSender();
        address newOwner = to;

        tokenExists.key = tokenId;
        if(tokenExists.value && Equal(currentOwner,ownerOf(tokenId)) && !Equal(currentOwner,newOwner) && newOwner!=0){
                //removeFromTokenList(tokenId);

                balances.key = currentOwner;
                balances.value = U256_Sub(balances.value, U256(1));
                //balances[currentOwner]-=1;

                tokenOwners.key=tokenId;
                tokenOwners.value = newOwner;
                //tokenOwners[tokenId] = newOwner;

                balances.key= newOwner;
                balances.value = U256_Add(balances.value, U256(1));
                //balances[newOwner]+=1;
                //
                Transfer(currentOwner,newOwner,tokenId);
        }
}

MUTABLE
void mint(uint256 tokenId){
  //艺术品ID查重
        tokenExists.key = tokenId;
  Assert("",tokenExists.value);

  tokenExists.value = true;

  //创作者账户代币+1，艺术品添加拥有者
  address creator = GetSender();
  PrintAddress("creator:", creator);
  balances.key = creator;
  balances.value = U256_Add(balances.value, U256(1));
  PrintUint256T("balances.value:", balances.value);
  
  tokenOwners.key = tokenId;
  tokenOwners.value = creator;
  PrintAddress("tokenOwners.value:", tokenOwners.value);
}



_() {}
