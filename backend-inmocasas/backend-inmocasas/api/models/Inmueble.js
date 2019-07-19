/**
 * Inmueble.js
 *
 * @description :: A model definition represents a database table/collection.
 * @docs        :: https://sailsjs.com/docs/concepts/models-and-orm/models
 */

module.exports = {

  attributes: {
    sector:{
      type:'string',
      required:true

    },
    callePrincipal:{
      type:'string',
    },
    calleSecundaria:{
      type:'string',
    },
    numeroPisos:{
      type:'number',
    },
    numeroHabitaciones:{
      type:'number',
    },
    numeroBanios:{
      type:'number',
    },
    numeroParqueaderos:{
      type:'number',
    },
    antiguedad:{
      type:'string',
    },
    ciudad:{
      type: 'string'
    },
     latitud:{
    type: 'number'
    },
    longitud:{
    type: 'number'
    },
    fkPublicacion:{
      model:'publicacion',
      unique:true
    },
  },
};

