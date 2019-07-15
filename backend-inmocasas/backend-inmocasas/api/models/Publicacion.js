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
    descripcion:{
      type:'string'
  },
  //FECHA DE PUBLICACIón se resuelve con Created At
    fkUsuario:{
      model:'usuario'
    },
    inmueblesDePublicacion:{
        collection: 'inmueble',
        via: 'fkPublicacion'

    }

  },

};

