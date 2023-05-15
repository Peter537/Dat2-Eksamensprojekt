difference()
{
    linear_extrude(height = 97.0, twist = 0.0, scale = 1.0, slices = 1, center = false)
    {
        scale([3090.0, 97.0])
        {
            M52();
        }
    }
    translate([0.0, 0.0, 52.0])
    {
        translate([1545.0, 0.0, 0.0])
        {
            linear_extrude(height = 45.0, twist = 0.0, scale = 1.0, slices = 1, center = false)
            {
                scale([390.0, 145.5])
                {
                    M52();
                }
            }
        }
    }
}

module M52()
{
    polygon
    (
        points =
        [
            [-0.5, -0.5], 
            [0.5, -0.5], 
            [0.5, 0.5], 
            [-0.5, 0.5]
        ],
        paths =
        [
            [0, 1, 2, 3]
        ]
    );
}
