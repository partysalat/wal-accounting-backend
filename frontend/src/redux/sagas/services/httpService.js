import axios from 'axios';


async function getInstance() {
  return axios.create({
    timeout: 10000,

  });
}


export default getInstance;
