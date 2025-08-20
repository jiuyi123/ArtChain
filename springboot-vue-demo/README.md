# springboot-vue-demo

## Project setup
```
npm install
```

### Compiles and hot-reloads for development
```
npm run serve
```

### Compiles and minifies for production
```
npm run build
```

### Customize configuration
See [Configuration Reference](https://cli.vuejs.org/config/).


###接口
1. 注册时区块链传地址(秘钥，用户地址能否获取)
2. NFT数据结构
   1. 艺术品唯一hash(能否发送)
   2. 历史交易记录:交易hash(能得到hash，能查到数据;)
   3. 
   ```
      `blockHash`: `DATA`, 32 Bytes - hash of the block where this transaction was in. `null` when its pending.
      - `blockNumber`: `QUANTITY` - block number where this transaction was in. `null` when its pending.
      - `from`: `DATA`, 20 Bytes - address of the sender.
      - `gas`: `QUANTITY` - gas provided by the sender.
      - `gasPrice`: `QUANTITY` - gas price provided by the sender in Wei.
      - `hash`: `DATA`, 32 Bytes - hash of the transaction.
      - `input`: `DATA` - the data send along with the transaction.
      - `nonce`: `QUANTITY` - the number of transactions made by the sender prior to this one.
      - `to`: `DATA`, 20 Bytes - address of the receiver. `null` when its a contract creation transaction.
      - `transactionIndex`: `QUANTITY` - integer of the transaction's index position in the block. `null` when its pending.
      - `value`: `QUANTITY` - value transferred in Wei.
      - `v`: `QUANTITY` - ECDSA recovery id
      - `r`: `DATA`, 32 Bytes - ECDSA signature r
      - `s`: `DATA`, 32 Bytes - ECDSA signature s
   ```
3. 上传作品  
   1. 点击创建，发送艺术品唯一hash和作者id
   2. 调用铸币函数(自定义，内含上链)(结点能不能打开)
   3. 区块链返回交易hash前端
   4. 前端存储到数据库中  
4. 交易  
   1. 点击购买，数据库卖方支付买方价格
   假设一:交易发生时一定安全
   假设二:用户
5. 历史记录的安全问题  
   方案一:在NFT里面保存历史nft的交易hash,(通过历史交易hash找到历史记录交易的区块)  
   问题1:因为交易的hash需要在交易完成后才能生成，因此交易的hash不能由交易本身进行存储.
所以修改历史记录需要再发起一笔新的交易，但这笔新的交易的hash未保存（套娃)。因此，可能出现
历史记录被篡改的情况。不安全（查交易hash生成时间)  
   问题2:根据交易hash得到的json里面中的参数input在当前测试链以及维特链上无法识别   
   方案二:每次交易在nft直接追加新的交易具体记录。（开销大）  
   方案三: 数据库存历史hash（必须有，但不用于安全性，仅提供查询，证实需要上链）
      通过nft的id在数据库里找到所有的交易的hash,再去
      问题1：数据库数据丢失
      问题2：数据篡改
           1.用不存在的交易hash替代，这样找不到区块链上对应的区块。
           2.用其他交易的hash替代，被欺骗。
6.题目
   基于区块链的艺术品数字资产确权流通系统实现艺术品的数字化 、上链并生成 
NFT、NFT 上架及下架、交易支付等功能。
   NFT 上架及下架
   
