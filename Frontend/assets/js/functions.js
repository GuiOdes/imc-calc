var lang = "pt-br"
var pageSize = 5
var page = 0

function pesquisaUsuario(idUsuario) {
    $.ajax({
      headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json',
      },
      url: "http://localhost:8080/person/list/"+idUsuario+"?lang="+lang,
      type: "GET"
    }).done(function(user) {
      $("#inputPeso").val(user.weight)
      $("#inputNome").val(user.name)
      $("#inputAltura").val(user.height)
    });
}

$("#form-pessoa").submit(function(e){
    e.preventDefault()
    var formulario = $(this);

    var valores = []; // array para guardar os valores do formulario
    
    for(var valor of formulario.serializeArray()){
        valores.push(valor.value);
    }

    if (valores[3] != "") {
        var url = "http://localhost:8080/person/update?lang="+lang
        var method = "PUT"
    } else {
        var url = "http://localhost:8080/person/create?lang="+lang
        var method = "POST"
    }

    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        },
        url: url,
        type: method,
        data: JSON.stringify({
            "name": valores[0],
            "weight": valores[1],
            "height": valores[2],
            "id": valores[3]
        })
    }).done(function(data) {
        $("#dados-usuario").html(
            "Nome: " + data.name +
            "<br>Peso: " + data.weight + " Kg" +
            "<br>Altura: " + data.height + " Cm" +
            "<br>IMC: " + data.bmi
        )
        $("#modalSucesso").modal('show')
    });
})

$("#btnCadastroAdicional").click(function(){
    $("#modalSucesso").modal('hide')
    $(".form-control").val("")
    $("#dados-usuario").html("")
})

function ajaxListagem() {
    $.ajax({
        url: "http://localhost:8080/person/list?pageSize=" + pageSize + "&page=" + page + "&lang=" + lang,
        type: "GET",
        context: document.body
    }).done(function( data ) {
        if (data.length > 0) {
            $("#lista-usuarios").html("")
            data.forEach(user => {
                $("#lista-usuarios").append('<div class="card" style="width: 18rem;"><div class="card-body"><h5 class="card-title">'+ user.name +'</h5><h6 class="card-subtitle mb-2 text-muted">'+user.bmi+'</h6><p class="card-text">Altura: '+user.height+'cm <br> Peso: '+user.weight+'kg</p><a href="novoUsuario.html?id='+user.id+'" class="card-link">Atualizar</a><a href="apagarUsuario.html?id='+user.id+'" class="card-link">Excluir</a></div></div>')
            });
        } else {
            page -= 1
        }
    });
}

function apagarUsuario(id) {
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        },
        url: "http://localhost:8080/person/delete/"+id,
        type: "DELETE"
    }).done(function() {
        $("#modalApagadoSucesso").modal('show')
    });
}

$("#resultadosPorPagina").change(function(){
    pageSize = parseInt($(this).val())
    ajaxListagem()
})

$("#idiomaClassificacao").change(function(){
    lang = $(this).val()
    ajaxListagem()  
})

$("#btn-proxima").click(function(){
    page += 1
    ajaxListagem()
})

$("#btn-anterior").click(function(){
    if (page > 0) {
        page -= 1
        ajaxListagem()
    }
})