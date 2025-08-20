module.exports = {
  networks: {
    development: {
      host: "127.0.0.1",
      port: 8888,
      network_id: '*', // Match any network id
      gas: 4700000,
      gasPrice: 20000000000,
      from: "0xd9f8401725d0bdec65f5215d5b0d16ac43ee8796"
    },
  },
};

