/**
 * Ciudad.js
 *
 * @description :: A model definition represents a database table/collection.
 * @docs        :: https://sailsjs.com/docs/concepts/models-and-orm/models
 */

module.exports = {

  attributes: {

 nombreCiudad:{
   type:'string'
 },
    inmueblesDeCiudad: {
      collection: 'inmueble',
      via: 'fkCiudad'
    }

  },

};

