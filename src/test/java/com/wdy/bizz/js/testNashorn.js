var TestUserService = Java.type("com.b1809.young.user.TestUserService");
var ArrayList = Java.type('java.util.ArrayList');
var HashMap = Java.type('java.util.HashMap');


var fun1 = function (name) {
    print('Hello ' + name);
    return "Hi!";
}

var fun2 = function (object) {
    print(Object.prototype.toString.call(object));
};

var fun3 = function () {
    var say = TestUserService.say("少时诵诗书所所所所所所所");
    print(say);
};

var fun4List = function () {
    var IntArray = Java.type("int[]");
    var array = new IntArray(5);
    array[0] = 5;
    array[1] = 4;
    array[2] = 3;
    array[3] = 2;
    array[4] = 1;
    try {
        array[5] = 23;
    } catch (e) {
        print(e.message);  // Array index out of range: 5
    }
    array[0] = "17";
    print(array[0]);  // 17

    array[0] = "wrong type";
    print(array[0]);  // 0
    array[0] = "17.3";
    print(array[0]);  // 17

    // 遍历数组
    var result = "";
    for each(var i
in
    array
)
    {
        result += i + "-";
    }
    print(result);

}

var fun5try = function () {
    try {
        throw "BOOM";
    } catch (e if
    typeof e === 'string'
)
    {
        print("String thrown: " + e);
    }
catch
    (e)
    {
        print("this shouldn't happen!");
    }
}


var fun6stream = function () {
    var list = new java.util.ArrayList();
    list.add("a1");
    list.add("a2");
    list.add("a3");
    list.add("b1");
    list.add("b2");
    list.stream().filter(function (e) {
        return e.startsWith("a");
    }).sorted()
        .forEach(function (a) {
            print(a);
        });
}


var fun7Thread = function () {
    var Runnable = Java.type("java.lang.Runnable");
    var Son = Java.extend(Runnable, {
        run: function () {
            print("开启一个新线程");
        }
    });
    var Thread = Java.type("java.lang.Thread");
    new Thread(new Son()).start();
    new Thread(function () {
        print("另一个线程");
    }).start();
}


var fun8Method = function () {
    var System = Java.type("java.lang.System");
    System.out.print("1");
    System.out["print"](2.0);
    System.out["println(double)"](3)
}

var fun9Bind = function () {
    var a1 = {};
    var a2 = {foo: 'bar'};
    Object.bindProperties(a1, a2);

    print(a1.foo);
    a1.foo = "   啊啊啊啊啊";
    print(a2.foo);
    print(a2.foo.trimLeft());
}

var fun10 = function () {
    print(__FILE__);
    print(__LINE__); // 122
    print(__DIR__);
}



