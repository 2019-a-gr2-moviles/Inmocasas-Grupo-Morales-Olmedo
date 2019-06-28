/**
 * Publicacion.js
 *
 * @description :: A model definition represents a database table/collection.
 * @docs        :: https://sailsjs.com/docs/concepts/models-and-orm/models
 */

module.exports = {

  attributes: {
  titulo:{
    type:'string',
    required:true

  },
    precio:{
      type: 'number',
      required:true
    },
    estado:{
      type:'string',
      isIn: ['pendiente', 'publico']

    },
    fechaPublicacion:{
    type:'string'
    },
    fkUsuario:{
      model:'usuario'
    },
    inmueble:{
        collection: 'inmueble',
        via: 'fkPublicacion'

    }

  },

};

