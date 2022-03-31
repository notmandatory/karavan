import React from 'react'
import "antd/dist/antd.css";
import {Content} from "antd/lib/layout/layout";
import {Button, Col, Descriptions, Divider, Form, Input, Row, Select, Typography} from "antd";
import TitleHeader from "../../Components/TitleHeader";
import {CustomLayout} from "../../Components/CustomLayout";
import axios from "axios";
import {decode as base64_decode, encode as base64_encode} from 'base-64';
import NavBar from "../../Components/Navbar/Navbar";

const { Option } = Select;
const { Title } = Typography;


const Catalog = () => {

    const scrollHeight = Math.max(
        document.body.scrollHeight,
        document.documentElement.scrollHeight,
        document.body.offsetHeight,
        document.documentElement.offsetHeight,
        document.body.clientHeight,
        document.documentElement.clientHeight
    );

    const TestApi1 = (values) => {
        const { network, descriptor } = values

        console.log(network);
        console.log(descriptor);

    };

    return (<CustomLayout>
        <NavBar isLoggedIn={true} />
        <Content
            style={{
                padding: "50px 50px",
                marginTop: "30px",
                height: `${scrollHeight}px`,
            }}
        >
            <Row justify="center">
                <Col xs={24}>
                    <TitleHeader
                        title="API Catalog"
                        size={2}
                    />
                </Col>
            </Row>
            <Row justify="center" style={{marginTop: "30px"}}>
                <Col xs={24} sm={18} md={12} lg={10} xl={5}>
                    <Form>
                        <Title level={3}>API1 Open Wallet</Title>
                        <Title level={5}>Parameters</Title>
                        <Form.Item
                            label="Network"
                            name="network"
                            rules={[
                                {
                                    required: true,
                                    message: "Please select Network!",
                                },
                            ]}
                            style={{
                                display: "flex"
                            }}
                        >
                            <Select style={{width: 120}}>
                                <Option value="testnet">testnet</Option>
                                <Option value="bitcoin">bitcoin</Option>
                                <Option value="regtest">regtest</Option>
                            </Select>
                        </Form.Item>
                        <Form.Item
                            label="Descriptor"
                            name="descriptor"
                            rules={[
                                {
                                    required: true,
                                    message: "Please input your Descriptor!",
                                },
                            ]}
                        >
                            <Input
                                placeholder="Descriptor"
                                style={{
                                    width: "40%",
                                    marginBottom: "10px",
                                }}
                            />
                        </Form.Item>

                        <Title level={5}>Result</Title>
                        <Descriptions title="">
                            <Descriptions.Item label="Status"></Descriptions.Item>
                            <Descriptions.Item label="Data"></Descriptions.Item>
                        </Descriptions>

                        <Form.Item>
                            <Button
                                type="primary"
                                onClick={TestApi1}
                                style={{
                                    width: "50%",
                                    marginBottom: "10px",
                                }}
                            >
                                Test Open Wallet
                            </Button>
                        </Form.Item>

                        <Divider
                            style={{
                                borderTop: "1px solid gray",
                            }}
                        />

                        <Title level={3}>API2 Get Wallet Balance</Title>

                        <Form.Item>
                            <Button
                                type="primary"
                                htmlType="submit"
                                style={{
                                    width: "50%",
                                    marginBottom: "10px",
                                }}
                            >
                                Test Get Wallet Balance
                            </Button>
                        </Form.Item>


                        <Divider
                            style={{
                                borderTop: "1px solid gray",
                            }}
                        />

                        <Title level={3}>API3 Close an Open Wallet</Title>

                        <Form.Item>
                            <Button
                                type="primary"
                                htmlType="submit"
                                style={{
                                    width: "50%",
                                    marginBottom: "10px",
                                }}
                            >
                                Test Close an Open Wallet
                            </Button>
                        </Form.Item>

                        <Divider
                            style={{
                                borderTop: "1px solid gray",
                            }}
                        />

                        <Title level={3}>API4 Get New Address</Title>

                        <Form.Item>
                            <Button
                                type="primary"
                                htmlType="submit"
                                style={{
                                    width: "50%",
                                    marginBottom: "10px",
                                }}
                            >
                                Test Get New Address
                            </Button>
                        </Form.Item>
                    </Form>


                </Col>
            </Row>
        </Content>
    </CustomLayout>);
};

export default Catalog;