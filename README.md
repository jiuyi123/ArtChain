# ArtChain: Blockchain-based Digital Art Asset Ownership and Circulation System  

## 📌 Introduction  
**ArtChain** is a blockchain-based system designed to solve ownership verification and circulation challenges in digital art asset trading. By integrating **NFT technology, smart contracts, and average-hash algorithms**, the platform ensures secure ownership certification, transparent circulation, efficient copyright protection, and compliance verification for digital artworks.  

## ✨ Key Features  
- **Artwork Operations**:  
  - Upload images, videos, audio, and documents  
  - Third-party compliance review  
  - NFT minting & blockchain integration  
  - Artwork listing, display, and trading  

- **User Services**:  
  - Registration, login, and account management  
  - Binding real-world identity with blockchain accounts  
  - Built-in wallet for simplified transactions (no external crypto wallets required)  

- **Platform Management**:  
  - System configuration  
  - User & artwork management  
  - Order management  

- **Core Innovations**:  
  - **Dual-Storage Model**: Hybrid of blockchain + database for both security and efficiency  
  - **Copyright Protection**: Average-hash algorithm to detect duplicates and prevent infringement  
  - **Content Compliance**: Third-party review mechanism for uploaded artworks  
  - **Simplified Access**: Integrated wallet lowers participation threshold compared to traditional NFT platforms  

## 🏗️ System Architecture  
The system consists of three major modules:  
1. **Web Server Module** – Handles user interaction, artwork display, purchase workflow, and management interface  
2. **Database Module** – Stores detailed user, artwork, and order information  
3. **Blockchain Module** – Records NFTs, ownership binding, and transaction hashes for traceability  

### Workflow Examples  
- **Upload & Minting**:  
  1. User uploads artwork → System generates unique ID & hash  
  2. Third-party review for compliance & similarity check  
  3. Smart contract mints NFT with Artwork ID + Owner’s blockchain account  

- **Trading**:  
  1. Buyer pays via integrated wallet  
  2. Smart contract transfers NFT ownership  
  3. Transaction hash stored in blockchain & mirrored in database  

## ⚙️ Installation & Environment  
### Requirements  
- **OS**: Ubuntu 16.04+ (Docker required for other versions)  
- **Languages/Tools**: Go ≥ 1.9.2, Node.js v8.11.2, GCC, Git  
- **Blockchain Components**:  
  - [gvnt](https://github.com/vntchain/go-vnt)  
  - [bottle](https://github.com/vntchain/bottle)  
  - [vnt.js](https://github.com/vntchain/vnt.js) 0.20.7  
  - [vnt-kit.js](https://github.com/vntchain/vnt-kit.js) 1.0.0  

### Setup Steps  
```bash
# 1. Install gvnt and add to PATH
git clone https://github.com/vntchain/go-vnt
cd go-vnt
make all

# 2. Clone and build bottle
git clone https://github.com/vntchain/bottle
cd bottle
make bottle
./build/bin/bottle

# 3. Install JS dependencies
npm install --save https://github.com/vntchain/vnt.js.git
npm install --save https://github.com/vntchain/vnt-kit.js.git
npm install --save ethereumjs-tx@1.3.7
npm install --save ethereumjs-account

# 4. Configure & run a local VNT test network (see official docs)

## 🔐 Smart Contract Design  
- **NFT Counter**: auto-increment unique ID  
- **Ownership Mapping**: Artwork ID → Owner’s blockchain address  
- **Token URI**: Artwork metadata binding  
- **Approval Mechanism**: Ownership transfer restricted to owner or approved accounts  

## 📊 Testing & Evaluation  
- **Functional Testing**: User info management, artwork upload, purchase flow, NFT minting, and ownership transfer  
- **Security**: Blockchain immutability ensures traceability; smart contract restricts unauthorized transfers  
- **Performance**: Dual-storage model balances efficiency and security; average-hash algorithm improves copyright detection speed and accuracy  

## 🌍 Application Scenarios  
- Digital artists seeking copyright protection  
- Collectors requiring transparent ownership verification  
- Platforms aiming to lower NFT participation barriers  

## 📈 Future Work  
- Explore flexible business models beyond fixed-price trading (e.g., auctions)  
- Enhance legal compliance frameworks for NFT transactions  
- Improve scalability and multi-chain compatibility  

## 📧 Contact  
Project Team – *Blockchain-based Digital Art Asset Ownership and Circulation System*  
Email: 741026400@qq.com  

