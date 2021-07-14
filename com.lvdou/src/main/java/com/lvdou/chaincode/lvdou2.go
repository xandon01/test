//绿豆余额合约

package main

import (
	"fmt"
	"unsafe"
	"github.com/hyperledger/fabric-contract-api-go/contractapi"
)

type SmartContract struct {
	contractapi.Contract
}


func Int2Byte(data int)(ret []byte){
       var len uintptr = unsafe.Sizeof(data)
       ret = make([]byte, len)
       var tmp int = 0xff
       var index uint = 0
       for index=0; index<uint(len); index++{
              ret[index] = byte((tmp<<(index*8) & data)>>(index*8))
       }
       return ret
}

func Byte2Int(data []byte)int{
       var ret int = 0
       var len int = len(data)
       var i uint = 0
       for i=0; i<uint(len); i++{
              ret = ret | (int(data[i]) << (i*8))
       }
       return ret
}


// 初始化1000绿豆
func (s *SmartContract) InitLedger(ctx contractapi.TransactionContextInterface) {
	err :=ctx.GetStub().PutState("123", Int2Byte(1000))
	
  	if err != nil {
		fmt.Printf("Failed to put to world state. %s", err.Error())
  	}
  
	err2 :=ctx.GetStub().PutState("456", Int2Byte(1000))
	
	if err2 != nil {
		fmt.Printf("Failed to put to world state. %s", err.Error())
  	}
}


// 查询余额
func (s *SmartContract) QueryBal(ctx contractapi.TransactionContextInterface, phone string) (int,error) {
	bal, err := ctx.GetStub().GetState(phone)
	
	if err != nil {
		return -1, fmt.Errorf("Failed to read from world state. %s", err.Error())
	}
	
	return Byte2Int(bal), nil
}



// 获得绿豆
func (s *SmartContract) Increase(ctx contractapi.TransactionContextInterface, phone string,  amt int){
	//1
	bal, err := ctx.GetStub().GetState(phone)
	
	if err != nil {
		fmt.Printf("Failed to read from world state. %s", err.Error())
		return
	}
	
	

	//增加
	balInt := Byte2Int(bal)
	balInt = balInt + amt
  
  	//3
  	err2 :=ctx.GetStub().PutState(phone, Int2Byte(balInt))
  	
  	if err2 != nil {
		fmt.Printf("Failed to put to world state. %s", err.Error())
  	}
 
}


// 花费绿豆
func (s *SmartContract) Decrease(ctx contractapi.TransactionContextInterface, phone string,  amt int){
	//1
	bal, err := ctx.GetStub().GetState(phone)
	
	if err != nil {
		fmt.Printf("Failed to read from world state. %s", err.Error())
		return
	}
	
	

	//减少
	balInt := Byte2Int(bal)
	balInt = balInt - amt
  
  	//3
  	err2 :=ctx.GetStub().PutState(phone, Int2Byte(balInt))
  	
  	if err2 != nil {
		fmt.Printf("Failed to put to world state. %s", err.Error())
  	}
 
}


// 转账
func (s *SmartContract) Transer(ctx contractapi.TransactionContextInterface, phone string, phone2 string, amt int){
	//1
	bal, err := ctx.GetStub().GetState(phone)
	
	if err != nil {
		fmt.Printf("Failed to read from world state. %s", err.Error())
		return
	}
	
	bal2, err := ctx.GetStub().GetState(phone2)
	
	if err != nil {
		fmt.Printf("Failed to read from world state. %s", err.Error())
		return
	}

	//计算
	balInt := Byte2Int(bal)
	bal2Int := Byte2Int(bal2)
	
	balInt = balInt - amt
  	bal2Int = bal2Int + amt
  
  	//3
  	err2 :=ctx.GetStub().PutState(phone, Int2Byte(balInt))
  	
  	if err2 != nil {
		fmt.Printf("Failed to put to world state. %s", err.Error())
  	}
  
	err3 :=ctx.GetStub().PutState(phone2, Int2Byte(bal2Int))
	
	if err3 != nil {
		fmt.Printf("Failed to put to world state. %s", err.Error())
  	}
}



func main() {

	chaincode, err := contractapi.NewChaincode(new(SmartContract))

	if err != nil {
		fmt.Printf("Error create lvdou chaincode: %s", err.Error())
		return
	}

	if err := chaincode.Start(); err != nil {
		fmt.Printf("Error starting lvdou chaincode: %s", err.Error())
	}
}
